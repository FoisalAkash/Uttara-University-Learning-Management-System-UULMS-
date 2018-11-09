package com.sky.coderx.uulms.AdminPanel.AddCourse;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sky.coderx.uulms.R;

public class CourseActivity extends AppCompatActivity {
EditText courseEditText;
Spinner courseCredit;
    Spinner course_panel_dept;
Button courseButton;
String[] cc,dd;
DatabaseReference courseReference;
ArrayAdapter<String> couseCredit_adapter,courseDept_adapter;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        getSupportActionBar().setTitle("Add Course");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(CourseActivity.this,R.color.DarkCyan));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00838c")));

        initview();
        courseReference = FirebaseDatabase.getInstance().getReference("course_database");



    }

    private void initview() {
        courseEditText=findViewById(R.id.courseName);
        courseCredit=findViewById(R.id.selectCourseCredit);
        courseButton=findViewById(R.id.saveCourseButton);
        course_panel_dept=findViewById(R.id.select_dept);

        cc = getResources().getStringArray(R.array.courseCredit);
        couseCredit_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cc);
        courseCredit.setAdapter(couseCredit_adapter);

        dd = getResources().getStringArray(R.array.dept);
        courseDept_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dd);
        course_panel_dept.setAdapter(courseDept_adapter);
    }

    public void saveCourseData(View view) {

        String CourseName,CourseCredit,CourseDept;
        CourseName=courseEditText.getText().toString().trim();
        CourseCredit = courseCredit.getSelectedItem().toString();
        CourseDept = course_panel_dept.getSelectedItem().toString();

        if (CourseName.isEmpty()) {
            courseEditText.setError("Please Enter A Course Name");
            courseEditText.requestFocus();
            return;
        }
        if(CourseCredit.equals("Select A Couse Credit"))
        {
            TextView errorText = (TextView)courseCredit.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Select A Course Credit");
            courseCredit.requestFocus();
            return;
        }
        if(CourseDept.equals("Select A Department"))
        {
            TextView errorText = (TextView)courseCredit.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Select A Departmentt");
            course_panel_dept.requestFocus();
            return;
        }

        courseReference.child(CourseDept).child(CourseName).setValue(new CourseInfo(CourseName,CourseCredit)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {



                    Toast.makeText(CourseActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    String compareValue = "Select A Couse Credit";
                    int spinnerPosition = couseCredit_adapter.getPosition(compareValue);
                    courseCredit.setSelection(spinnerPosition);
                    course_panel_dept.setSelection(spinnerPosition);





                    courseEditText.setText("");


                } else {
                    Toast.makeText(CourseActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });





    }
}
