package com.sky.coderx.uulms.AdminPanel.ViewFaculty;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.sky.coderx.uulms.AdminPanel.AddFaculty.FacultyInfo;
import com.sky.coderx.uulms.AdminPanel.AddFaculty.FacultyList;
import com.sky.coderx.uulms.R;

import java.util.ArrayList;
import java.util.List;

public class ViewFacultyActivity extends AppCompatActivity {

    ListView facultylistView;
    List<FacultyInfo> facultyList;
    DatabaseReference facultyreference;
    FacultyList fadapter;

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        getSupportActionBar().setTitle("Faculty List");

        getWindow().setStatusBarColor(ContextCompat.getColor(ViewFacultyActivity.this,R.color.DarkPurpale));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bd00d6")));

        facultyreference = FirebaseDatabase.getInstance().getReference("faculty_database");


        facultylistView=findViewById(R.id.facultyList);
        facultyList=new ArrayList<>();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        facultyreference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                facultyList.clear();
                for(DataSnapshot facultyDeptSnapshot : dataSnapshot.getChildren())
                {
                    for(DataSnapshot facultyIDSnapshot : facultyDeptSnapshot.getChildren()) {
                        FacultyInfo faculty = facultyIDSnapshot.getValue(FacultyInfo.class);
                        facultyList.add(faculty);
                    }
                }

                fadapter=new FacultyList(ViewFacultyActivity.this,facultyList);
                facultylistView.setAdapter(fadapter);
                facultylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final FacultyInfo facultyInfo=facultyList.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewFacultyActivity.this);
                        builder.setCancelable(false);
                        String alertname=facultyList.get(position).getName();
                        String alertdept=facultyList.get(position).getDept();
                        String alertemail=facultyList.get(position).getEmail();
                        String alertusername=facultyList.get(position).getUsername();
                        String alertpassword=facultyList.get(position).getPassword();
                        String alertdegree=facultyList.get(position).getDegree();
                        String alertnumber=facultyList.get(position).getMobile_number();
                        View root =getLayoutInflater().inflate(
                                (R.layout.facultytextview),null,true);

                        TextView textView =(TextView)root.findViewById(R.id.alertData);
                        TextView TextView2=root.findViewById(R.id.alertTitle);

                        SpannableString content = new SpannableString("Faculty Full Information");
                        content.setSpan(new UnderlineSpan(), 0,content.length(), 0);
                        TextView2.setText(content);



                        textView.setText(Html.fromHtml("Name: <font color='#bd00d6'>"+alertname+"</font> <br>Department: <font color='#bd00d6'>"+alertdept
                                +"</font> <br>E-mail: <font color='#bd00d6'>"+alertemail+"</font> <br>Username: <font color='#bd00d6'>"+alertusername
                                +"</font> <br>Password: <font color='#bd00d6'>"+alertpassword+"</font> <br>Degree: <font color='#bd00d6'>"+alertdegree+"</font> <br>Contact Number: <font color='#bd00d6'>"+alertnumber+"</font>"));
                        builder.setView(root);
                        builder.setNegativeButton("Back", null);
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                facultyreference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        facultyreference.child(facultyInfo.getDept()).child(facultyInfo.getUser_id()).removeValue();
                                        fadapter.notifyDataSetChanged();
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
                Toast.makeText(ViewFacultyActivity.this,""+databaseError,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
