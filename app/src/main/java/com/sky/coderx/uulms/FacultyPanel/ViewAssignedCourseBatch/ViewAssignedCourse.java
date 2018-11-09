package com.sky.coderx.uulms.FacultyPanel.ViewAssignedCourseBatch;

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

public class ViewAssignedCourse extends AppCompatActivity {
    ListView courselistView;
    List<AssignCourseInfo> courseList;
String facultyuser_id;
DatabaseReference assignedCourseRef;
String mycourse,fId;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assigned_course);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.DarkRed));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f7060a")));

        getSupportActionBar().setTitle("Assigned Course and Batch");

        assignedCourseRef=FirebaseDatabase.getInstance().getReference("AssignCourse_database");



        facultyuser_id= getIntent().getStringExtra("fid");
        courselistView=findViewById(R.id.myAssignedCourse);
        courseList=new ArrayList<>();
    }
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        assignedCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                courseList.clear();
                for(DataSnapshot assigncourseSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot Snapshot : assigncourseSnapshot.getChildren()) {
                        AssignCourseInfo faculty = Snapshot.getValue(AssignCourseInfo.class);
                        String fid = Snapshot.getValue(AssignCourseInfo.class).getFaculty_id();
                        if (fid.equals(facultyuser_id)) {
                            courseList.add(faculty);
                        }

                    }
                }

                AssignedCourseList assignedCourseListAdapter=new AssignedCourseList(ViewAssignedCourse.this,courseList);
                courselistView.setAdapter(assignedCourseListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),""+databaseError,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
