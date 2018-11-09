package com.sky.coderx.uulms.StudentPanel.StdCoursewithFacultyInfo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty.AssignCourseInfo;
import com.sky.coderx.uulms.R;

import java.util.ArrayList;
import java.util.List;

public class StdViewCourse extends AppCompatActivity {
    ListView stdcourselistView;
    List<AssignCourseInfo> stdcourseList;
    String stduser_batch;
    DatabaseReference stdCourseRef;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_view_course);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.DarkPurpale));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bd00d6")));

        getSupportActionBar().setTitle("Course and Faculty");

        stdCourseRef=FirebaseDatabase.getInstance().getReference("AssignCourse_database");



        stduser_batch= getIntent().getStringExtra("stdbatch");
        stdcourselistView=findViewById(R.id.myCourse);
        stdcourseList=new ArrayList<AssignCourseInfo>();
    }
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        stdCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                stdcourseList.clear();
                for(DataSnapshot assignSnapshot : dataSnapshot.getChildren())
                {
                    for(DataSnapshot assigncourseSnapshot : assignSnapshot.getChildren())
                    {
                        AssignCourseInfo std = assigncourseSnapshot.getValue(AssignCourseInfo.class);
                        std.getCourseName();
                        String stdb=assigncourseSnapshot.getValue(AssignCourseInfo.class).getBatchNumber();
                        if(stdb.equals(stduser_batch)) {
                            stdcourseList.add(std);
                        }

                    }
                }

                StdCourseList stdCourseListAdapter=new StdCourseList(StdViewCourse.this,stdcourseList);
                stdcourselistView.setAdapter(stdCourseListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),""+databaseError,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
