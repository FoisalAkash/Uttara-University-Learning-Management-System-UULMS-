package com.sky.coderx.uulms.AdminPanel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sky.coderx.uulms.AdminPanel.AddFaculty.AddFacultyActivity;
import com.sky.coderx.uulms.AdminPanel.AddStudent.AddStudentActivity;
import com.sky.coderx.uulms.AdminPanel.ViewFaculty.ViewFacultyActivity;
import com.sky.coderx.uulms.AdminPanel.ViewStudent.ViewStdActivity;
import com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty.AssignCourseActivity;
import com.sky.coderx.uulms.AdminPanel.AddCourse.CourseActivity;
import com.sky.coderx.uulms.LoginPage.AdminLoginPage;
import com.sky.coderx.uulms.R;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {
CardView addStd,addFaculty,viewStd,viewFaculty,course,assignCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        getSupportActionBar().setTitle("Admin Panel");

        addStd=findViewById(R.id.stdCardView);
        addFaculty=findViewById(R.id.facultyCardView);
        viewStd=findViewById(R.id.viewstdCardView);
        viewFaculty=findViewById(R.id.viewfacultyCardView);
        course=findViewById(R.id.courseCardView);
        assignCourse=findViewById(R.id.assignCourseCardView);

        addStd.setOnClickListener(this);
        addFaculty.setOnClickListener(this);
        viewStd.setOnClickListener(this);
        viewFaculty.setOnClickListener(this);
        course.setOnClickListener(this);
        assignCourse.setOnClickListener(this);


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
            Intent logoutIntent= new Intent(getApplicationContext(),AdminLoginPage.class);
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

        if(v.getId()==R.id.stdCardView)
        {
            Intent intent=new Intent(AdminPanel.this,AddStudentActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.facultyCardView)
        {
            Intent intent=new Intent(AdminPanel.this,AddFacultyActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.viewstdCardView)
        {
            Intent intent=new Intent(AdminPanel.this,ViewStdActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.viewfacultyCardView)
        {
            Intent intent=new Intent(AdminPanel.this,ViewFacultyActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.courseCardView)
        {
            Intent intent=new Intent(AdminPanel.this,CourseActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.assignCourseCardView)
        {
            Intent intent=new Intent(AdminPanel.this,AssignCourseActivity.class);
            startActivity(intent);
        }

    }

}
