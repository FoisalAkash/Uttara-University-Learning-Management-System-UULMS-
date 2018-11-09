package com.sky.coderx.uulms.AdminPanel.ViewStudent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AddStudent.StudentInfo;
import com.sky.coderx.uulms.AdminPanel.AddStudent.StudentList;
import com.sky.coderx.uulms.R;

import java.util.ArrayList;
import java.util.List;

public class ViewStdActivity extends AppCompatActivity  {
ListView stdlistView;
List<StudentInfo> studentList;
    DatabaseReference reference;

    StudentList adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_std);

        getSupportActionBar().setTitle("Student List");

        reference = FirebaseDatabase.getInstance().getReference("student_database");


        stdlistView=findViewById(R.id.stdList);
        studentList=new ArrayList<>();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        reference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                studentList.clear();
                for(DataSnapshot studentDeptSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot studentBatchSnapshot : studentDeptSnapshot.getChildren()) {
                        for (DataSnapshot studentIDSnapshot : studentBatchSnapshot.getChildren()) {
                            StudentInfo student = studentIDSnapshot.getValue(StudentInfo.class);
                            studentList.add(student);
                        }

                    }
                }

                adapter=new StudentList(ViewStdActivity.this,studentList);
                stdlistView.setAdapter(adapter);
                stdlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final StudentInfo studentInfo=studentList.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewStdActivity.this);
                        builder.setCancelable(false);
                        String alertname=studentList.get(position).getName();
                        String alertid=studentList.get(position).getId();
                        String alertbatch=studentList.get(position).getBatch();
                        String alertdept=studentList.get(position).getDept();
                        String alertemail=studentList.get(position).getEmail();
                        String alertusername=studentList.get(position).getUsername();
                        String alertpassword=studentList.get(position).getPassword();
                        String alertnumber=studentList.get(position).getMobile_number();
                        System.out.println(alertname);
                        View root =getLayoutInflater().inflate(
                                (R.layout.stdtextview),null,true);

                        TextView textView =(TextView)root.findViewById(R.id.alertData);
                        TextView TextView2=root.findViewById(R.id.alertTitle);

                        SpannableString content = new SpannableString("Student Full Information");
                        content.setSpan(new UnderlineSpan(), 0,content.length(), 0);
                        TextView2.setText(content);



                        textView.setText(Html.fromHtml("Name: <font color='blue'>"+alertname+"</font> <br>Id: <font color='blue'>"+alertid
                                          +"</font> <br>Batch: <font color='blue'>"+alertbatch+"</font> <br>Department: <font color='blue'>"+alertdept
                                          +"</font> <br>E-mail: <font color='blue'>"+alertemail+"</font> <br>Username: <font color='blue'>"+alertusername
                                          +"</font> <br>Password: <font color='blue'>"+alertpassword+"</font> <br>Contact Number: <font color='blue'>"+alertnumber+"</font>"));
                        builder.setView(root);
                        builder.setNegativeButton("Back", null);
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        reference.child(studentInfo.getDept()).child(studentInfo.getBatch()).child(studentInfo.getId()).removeValue();
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        builder.show();





                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewStdActivity.this,""+databaseError,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
