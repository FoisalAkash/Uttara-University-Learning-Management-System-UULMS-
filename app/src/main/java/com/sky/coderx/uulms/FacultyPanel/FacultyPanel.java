package com.sky.coderx.uulms.FacultyPanel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sky.coderx.uulms.FacultyPanel.Feedback.FeedBack;
import com.sky.coderx.uulms.FacultyPanel.Personalinfo.PersonalInfo;
import com.sky.coderx.uulms.FacultyPanel.UploadFile.UploadFile;
import com.sky.coderx.uulms.FacultyPanel.ViewAssignedCourseBatch.ViewAssignedCourse;
import com.sky.coderx.uulms.LoginPage.FacultyLoginPage;
import com.sky.coderx.uulms.R;

public class FacultyPanel extends AppCompatActivity  implements View.OnClickListener {
    CardView personalInfo,assignedCourse,feedback,uploadFile;
    String FacultyID,FacultyDEPT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_panel);

        getSupportActionBar().setTitle("Faculty Panel");

       FacultyID = getIntent().getStringExtra("fuser_id");
        FacultyDEPT = getIntent().getStringExtra("fuser_dept");

        personalInfo=findViewById(R.id.facultyInfoCardView);
        assignedCourse=findViewById(R.id.acCardView);
        feedback=findViewById(R.id.feedbackCardView);
        uploadFile=findViewById(R.id.uploadFileCardView);

        personalInfo.setOnClickListener(this);
        assignedCourse.setOnClickListener(this);
        feedback.setOnClickListener(this);
        uploadFile.setOnClickListener(this);
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
            Intent logoutIntent= new Intent(getApplicationContext(),FacultyLoginPage.class);
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
        if(v.getId()==R.id.facultyInfoCardView)
        {
            Intent intent=new Intent(FacultyPanel.this,PersonalInfo.class);
            intent.putExtra("fid",FacultyID);
            startActivity(intent);
        }
        else if(v.getId()==R.id.acCardView)
        {
            Intent intent=new Intent(FacultyPanel.this,ViewAssignedCourse.class);
            intent.putExtra("fid",FacultyID);
            startActivity(intent);
        }
        else if(v.getId()==R.id.feedbackCardView)
        {
            Intent intent=new Intent(FacultyPanel.this,FeedBack.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.uploadFileCardView)
        {
            Intent intent=new Intent(FacultyPanel.this,UploadFile.class);
            intent.putExtra("fid",FacultyID);
            intent.putExtra("fdept",FacultyDEPT);
            startActivity(intent);
        }
    }
}
