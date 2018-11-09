package com.sky.coderx.uulms.MainActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.sky.coderx.uulms.LoginPage.AdminLoginPage;
import com.sky.coderx.uulms.LoginPage.FacultyLoginPage;
import com.sky.coderx.uulms.R;
import com.sky.coderx.uulms.LoginPage.StudentLoginPage;

public class LoginType extends AppCompatActivity {
Button stdButton,facultyButton,adminButton;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        getSupportActionBar().hide();

        stdButton=findViewById(R.id.stdLogin);
        facultyButton=findViewById(R.id.facultyLogin);
        adminButton=findViewById(R.id.adminLogin);


        stdButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {


                    stdButton.setTextColor(Color.BLACK);
                    stdButton.setBackground(getResources().getDrawable(R.drawable.pressed_button_shap));
                    Intent intent= new Intent(LoginType.this,StudentLoginPage.class);
                    startActivity(intent);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stdButton.setTextColor(Color.WHITE);
                    stdButton.setBackground(getResources().getDrawable(R.drawable.blue_button_shap));

                }
                return true;
            }
        });


        facultyButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {


                    facultyButton.setTextColor(Color.BLACK);
                    facultyButton.setBackground(getResources().getDrawable(R.drawable.pressed_button_shap));
                    Intent intent= new Intent(LoginType.this,FacultyLoginPage.class);
                    startActivity(intent);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    facultyButton.setTextColor(Color.WHITE);
                    facultyButton.setBackground(getResources().getDrawable(R.drawable.blue_button_shap));

                }
                return true;
            }
        });


        adminButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {


                    adminButton.setTextColor(Color.BLACK);
                    adminButton.setBackground(getResources().getDrawable(R.drawable.pressed_button_shap));
                    Intent intent= new Intent(LoginType.this,AdminLoginPage.class);
                    startActivity(intent);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    adminButton.setTextColor(Color.WHITE);
                    adminButton.setBackground(getResources().getDrawable(R.drawable.blue_button_shap));

                }
                return true;
            }
        });



    }

}
