package com.sky.coderx.uulms.FacultyPanel.Personalinfo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AddFaculty.FacultyInfo;
import com.sky.coderx.uulms.R;

public class PersonalInfo extends AppCompatActivity {
TextView personalInfo;
DatabaseReference finfoReference;
String facultyuser_id,faculty,facultyID;
    String alertname,alertdept,alertemail,alertusername,alertpassword,alertdegree,alertnumber;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getWindow().setStatusBarColor(ContextCompat.getColor(PersonalInfo.this,R.color.DarkGreen));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2fc24a")));

        getSupportActionBar().setTitle("My Personal Infoimation");

        finfoReference=FirebaseDatabase.getInstance().getReference("faculty_database");



        personalInfo=findViewById(R.id.personalInfo);

        facultyuser_id= getIntent().getStringExtra("fid");


    }
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        finfoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                for(DataSnapshot facultyDeptSnapshot : dataSnapshot.getChildren())
                {
                    for(DataSnapshot facultyIDSnapshot : facultyDeptSnapshot.getChildren()) {

                        facultyID=facultyIDSnapshot.getValue(FacultyInfo.class).getUser_id();

                        if(facultyID.equals(facultyuser_id))
                        {

                            faculty=facultyIDSnapshot.getValue(FacultyInfo.class).getUser_id();
                            alertname=facultyIDSnapshot.getValue(FacultyInfo.class).getName();
                            alertdept=facultyIDSnapshot.getValue(FacultyInfo.class).getDept();
                            alertemail=facultyIDSnapshot.getValue(FacultyInfo.class).getEmail();
                            alertusername=facultyIDSnapshot.getValue(FacultyInfo.class).getUsername();
                            alertpassword=facultyIDSnapshot.getValue(FacultyInfo.class).getPassword();
                            alertdegree=facultyIDSnapshot.getValue(FacultyInfo.class).getDegree();
                            alertnumber=facultyIDSnapshot.getValue(FacultyInfo.class).getMobile_number();
                        }

                    }
                }
                if(faculty.equals(facultyuser_id))
                {
                    personalInfo.setText(Html.fromHtml("Name: <font color='#2fc24a'>"+alertname+"</font> <br>Department: <font color='#2fc24a'>"+alertdept
                        +"</font> <br>E-mail: <font color='#2fc24a'>"+alertemail+"</font> <br>Username: <font color='red'>"+alertusername
                        +"</font> <br>Password: <font color='red'>"+alertpassword+"</font> <br>Degree: <font color='#2fc24a'>"+alertdegree+"</font> <br>Contact Number: <font color='#2fc24a'>"+alertnumber+"</font>"));

                }




                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            }

}
