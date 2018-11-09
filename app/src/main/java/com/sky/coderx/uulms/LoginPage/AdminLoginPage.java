package com.sky.coderx.uulms.LoginPage;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sky.coderx.uulms.AdminPanel.AdminPanel;
import com.sky.coderx.uulms.R;

public class AdminLoginPage extends AppCompatActivity {
    EditText usernameEditText,passwordEditText;
    Button loginButton;
    private FirebaseAuth firebaseAuth;
    DatabaseReference student_reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

       getSupportActionBar().hide();


        initview();

    }
    private void initview() {
       usernameEditText=findViewById(R.id.usernameId);
       passwordEditText=findViewById(R.id.passwordId);
       loginButton=findViewById(R.id.loginButtonId);
       firebaseAuth=FirebaseAuth.getInstance();
        student_reference = FirebaseDatabase.getInstance().getReference("student_database");

       loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {


                    loginButton.setTextColor(Color.BLACK);
                    loginButton.setBackground(getResources().getDrawable(R.drawable.pressed_button_shap));
                    final String username,password;
                    username = usernameEditText.getText().toString().trim();
                    password = passwordEditText.getText().toString().trim();

                    if(username.isEmpty())
                    {
                        usernameEditText.setError("Please Enter A Username");
                        usernameEditText.requestFocus();
                        return true;
                    }
                    if(password.isEmpty())
                    {
                        passwordEditText.setError("Please Enter A Password");
                        passwordEditText.requestFocus();
                        return true;
                    }

                    firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                Intent intent= new Intent(AdminLoginPage.this,AdminPanel.class);
                                startActivity(intent);
                                Toast.makeText(AdminLoginPage.this, "login successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else
                            {
                                Toast.makeText(AdminLoginPage.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    loginButton.setTextColor(Color.WHITE);
                    loginButton.setBackground(getResources().getDrawable(R.drawable.blue_button_shap));

                }
                return true;
            }
        });

    }


}
