package com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AddFaculty.FacultyInfo;
import com.sky.coderx.uulms.R;

import java.util.ArrayList;

public class AssignCourseActivity extends AppCompatActivity {
    Spinner batchSelectSpinner,deptSelectSpinner,courseSelectSpinner,facultySelectSpinner;
    Button assignCourseButton;
    String selectedBatch,selectedDept,selectedCourse,selectedfaculty,faculty_user_id,faculty_user_dept;
    ArrayAdapter<String> deptSelect;
    ArrayAdapter<String> batchSelect;
    ArrayAdapter<String> courseSelect;
    ArrayAdapter<String> facultySelect;
    DatabaseReference batchReference,courseReference,assignfacultyReferance,assign_Course;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_course);

        getSupportActionBar().setTitle("Assign Course");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(AssignCourseActivity.this,R.color.DarkCerise));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#df0091")));

        initview();
        batchReference = FirebaseDatabase.getInstance().getReference("student_database");
        courseReference = FirebaseDatabase.getInstance().getReference("course_database");
     assignfacultyReferance = FirebaseDatabase.getInstance().getReference("faculty_database");
   assign_Course = FirebaseDatabase.getInstance().getReference("AssignCourse_database");
    }

    private void initview() {
        deptSelectSpinner=findViewById(R.id.selectDeptForAssignCourse);
        batchSelectSpinner=findViewById(R.id.selectBatchForAssignCourse);
        courseSelectSpinner=findViewById(R.id.selectCourse);
        facultySelectSpinner=findViewById(R.id.selectfaculty);
        assignCourseButton=findViewById(R.id.assignCourseButton);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final ArrayList<String> dept = new ArrayList<>();
        batchReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dept.clear();
                dept.add("---Select a dept---");
                for(DataSnapshot studentdeptSnapshot : dataSnapshot.getChildren()) {
                    String e = studentdeptSnapshot.getKey();
                    Log.e("d",e);

                        courseSelectSpinner.setEnabled(true);
                        dept.add(e);

                }


                deptSelect = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_item, dept);
                deptSelectSpinner.setAdapter(deptSelect);
                deptSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedDept=deptSelectSpinner.getSelectedItem().toString();
                        deptm();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void deptm() {
        final ArrayList<String> batch = new ArrayList<>();
        batchReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                batch.clear();
                batch.add("---Select a batch---");
                for(DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    String b = studentSnapshot.getKey();
                    for (DataSnapshot studentBatchSnapshot : studentSnapshot.getChildren()) {

                        if (selectedDept.equals(b)) {
                            batchSelectSpinner.setEnabled(true);

                            String d =studentBatchSnapshot.getKey();
                            batch.add(d);
                        }
                    }
                }


                batchSelect = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_item, batch);
                batchSelectSpinner.setAdapter(batchSelect);
                batchSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch=batchSelectSpinner.getSelectedItem().toString();




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final ArrayList<String> course = new ArrayList<>();
        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                course.clear();

                course.add("---Select a course---");
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    String c = Snapshot.getKey();
                    for (DataSnapshot studentBatchSnapshot : Snapshot.getChildren()) {

                        if (selectedDept.equals(c)) {
                            courseSelectSpinner.setEnabled(true);

                            String d = studentBatchSnapshot.getValue(AssignCourseInfo.class).getCourseName();
                            course.add(d);
                        }
                    }
                }


                courseSelect = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_item, course);
                courseSelectSpinner.setAdapter(courseSelect);
                courseSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCourse = courseSelectSpinner.getSelectedItem().toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final ArrayList<String> faculty = new ArrayList<>();
        assignfacultyReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                faculty.clear();

                faculty.add("---Select a faculty---");
                for(DataSnapshot Snapshot : dataSnapshot.getChildren())
                {
                    String fd=Snapshot.getKey();
                    Log.e("fd",fd);
                    for(DataSnapshot facultySnapshot : Snapshot.getChildren()) {
                        if(selectedDept.equals(fd))
                        {
                            String f=facultySnapshot.getValue(FacultyInfo.class).getName();;
                            faculty_user_id=facultySnapshot.getValue(FacultyInfo.class).getUser_id();;
                            faculty_user_dept=facultySnapshot.getValue(FacultyInfo.class).getDept();
                            faculty.add(f);
                        }

                    }

                }

                facultySelect = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_item, faculty);
                facultySelectSpinner.setAdapter(facultySelect);
                facultySelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedfaculty=facultySelectSpinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void saveAssignCourse(View view) {
        if(selectedBatch.equals("---Select a batch---")||selectedCourse.equals("---Select a course---")||selectedfaculty.equals("---Select a faculty---"))
        {
            Toast.makeText(getApplicationContext(),"select a course,batch & faculty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String uid=assign_Course.push().getKey();
            assign_Course.child(faculty_user_dept).child(uid).setValue(new AssignCourseInfo(selectedCourse,selectedBatch,selectedfaculty,faculty_user_id)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        int spinnerPosition =0;
                        deptSelectSpinner.setSelection(spinnerPosition);
                        batchSelectSpinner.setSelection(spinnerPosition);
                        courseSelectSpinner.setSelection(spinnerPosition);
                        facultySelectSpinner.setSelection(spinnerPosition);

                        Toast.makeText(AssignCourseActivity.this, "Saved", Toast.LENGTH_SHORT).show();



                    } else {
                        Toast.makeText(AssignCourseActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }
}
