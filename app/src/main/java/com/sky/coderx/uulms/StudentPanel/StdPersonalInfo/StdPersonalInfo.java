package com.sky.coderx.uulms.StudentPanel.StdPersonalInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AddStudent.StudentInfo;
import com.sky.coderx.uulms.R;

public class StdPersonalInfo extends AppCompatActivity {
    TextView stdpersonalInfo;
    DatabaseReference stdinfoReference;
    String stduser_id,stduser_batch,sid,sbatch,studentID,studentBatch;
    String stdalertname,stdalertid,stdalertbatch,stdalertdept,stdalertemail,stdalertusername,stdalertpassword,stdalertnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_personal_info);

        getSupportActionBar().setTitle("My Personal Infoimation");

        stdinfoReference=FirebaseDatabase.getInstance().getReference("student_database");



        stdpersonalInfo=findViewById(R.id.stdpersonalInfo);

        stduser_id= getIntent().getStringExtra("stdid");
        stduser_batch= getIntent().getStringExtra("stdbatch");
        Log.e("id",stduser_id);
Log.e("batch",stduser_batch);

    }
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        stdinfoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for(DataSnapshot facultySnapshot : dataSnapshot.getChildren())
                {

                    for(DataSnapshot facultyDeptSnapshot : facultySnapshot.getChildren()) {
                        for (DataSnapshot facultyIDSnapshot : facultyDeptSnapshot.getChildren()) {

                            studentID = facultyIDSnapshot.getValue(StudentInfo.class).getId();
                            studentBatch = facultyIDSnapshot.getValue(StudentInfo.class).getBatch();
                            Log.e("ID", studentID);
                            Log.e("BA", studentBatch);

                            if (studentID.equals(stduser_id) && studentBatch.equals(stduser_batch)) {

                                stdalertid = facultyIDSnapshot.getValue(StudentInfo.class).getId();
                                stdalertbatch = facultyIDSnapshot.getValue(StudentInfo.class).getBatch();
                                stdalertname = facultyIDSnapshot.getValue(StudentInfo.class).getName();
                                stdalertdept = facultyIDSnapshot.getValue(StudentInfo.class).getDept();
                                stdalertemail = facultyIDSnapshot.getValue(StudentInfo.class).getEmail();
                                stdalertusername = facultyIDSnapshot.getValue(StudentInfo.class).getUsername();
                                stdalertpassword = facultyIDSnapshot.getValue(StudentInfo.class).getPassword();
                                stdalertnumber = facultyIDSnapshot.getValue(StudentInfo.class).getMobile_number();
                            }

                        }
                    }
                }
                if(stdalertid.equals(stduser_id)&&stdalertbatch.equals(stduser_batch))
                {
                    stdpersonalInfo.setText(Html.fromHtml("Name: <font color='#0822c9'>"+stdalertname+"</font> <br>ID: <font color='#0822c9'>"+stdalertid+"</font> <br>Batch: <font color='#0822c9'>"
                            +stdalertbatch+"</font> <br>Department: <font color='#0822c9'>"+stdalertdept
                            +"</font> <br>E-mail: <font color='#0822c9'>"+stdalertemail+"</font> <br>Username: <font color='red'>"+stdalertusername
                            +"</font> <br>Password: <font color='red'>"+stdalertpassword+"</font> <br>Contact Number: <font color='#0822c9'>"
                            +stdalertnumber+"</font>"));

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
