package com.sky.coderx.uulms.StudentPanel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sky.coderx.uulms.LoginPage.StudentLoginPage;
import com.sky.coderx.uulms.R;
import com.sky.coderx.uulms.StudentPanel.StdCoursewithFacultyInfo.StdViewCourse;
import com.sky.coderx.uulms.StudentPanel.StdFeedback.StdFeedBack;
import com.sky.coderx.uulms.StudentPanel.StdFileView.StdViewFile;
import com.sky.coderx.uulms.StudentPanel.StdPersonalInfo.StdPersonalInfo;

public class StudentPanel extends AppCompatActivity implements View.OnClickListener {
    CardView personalInfo,viewCourse,stdfeedback,viewFile;
    String stdId,stdBatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

        getSupportActionBar().setTitle("Student Panel");

        stdId=getIntent().getStringExtra("stdid");
        stdBatch=getIntent().getStringExtra("stdbatch");
        Log.e("stdID",stdId);
        Log.e("stdBATCH",stdBatch);

        personalInfo=findViewById(R.id.stdInfoCardView);
        viewCourse=findViewById(R.id.stdCourseCardView);
        stdfeedback=findViewById(R.id.stdfeedbackCardView);
        viewFile=findViewById(R.id.stdViewFileCardView);

        personalInfo.setOnClickListener(this);
        viewCourse.setOnClickListener(this);
        stdfeedback.setOnClickListener(this);
        viewFile.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            Toast.makeText(getApplicationContext(),"Logout Successfully! ",Toast.LENGTH_SHORT).show();
            Intent logoutIntent= new Intent(getApplicationContext(),StudentLoginPage.class);
            startActivity(logoutIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        Toast.makeText(getApplicationContext(),"Please press the logout button",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.stdInfoCardView)
        {
            Intent intent=new Intent(StudentPanel.this,StdPersonalInfo.class);
            intent.putExtra("stdid",stdId);
            intent.putExtra("stdbatch",stdBatch);
            Log.e("stdid",stdId);
            Log.e("stdbatch",stdBatch);
            startActivity(intent);
        }
        else if(v.getId()==R.id.stdCourseCardView)
        {
            Intent intent=new Intent(StudentPanel.this,StdViewCourse.class);
            intent.putExtra("stdid",stdId);
            intent.putExtra("stdbatch",stdBatch);
            startActivity(intent);
        }
        else if(v.getId()==R.id.stdfeedbackCardView)
        {
            Intent intent=new Intent(StudentPanel.this,StdFeedBack.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.stdViewFileCardView)
        {
            Intent intent=new Intent(StudentPanel.this,StdViewFile.class);
            intent.putExtra("stdid",stdId);
            intent.putExtra("stdbatch",stdBatch);
            startActivity(intent);
        }
    }
}
