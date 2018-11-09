package com.sky.coderx.uulms.LoginPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AddStudent.StudentInfo;
import com.sky.coderx.uulms.R;
import com.sky.coderx.uulms.StudentPanel.StudentPanel;

public class StudentLoginPage extends AppCompatActivity {

    TextView forgotPasswordTextView;
    EditText usernameEditText, passwordEditText;
    Button loginButton;
    private FirebaseAuth firebaseAuth;
    DatabaseReference student_reference;
    int flage=0;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login_page);
        getSupportActionBar().hide();
        /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        getSupportActionBar().hide();

        initview();

    }

    private void initview() {
        usernameEditText = findViewById(R.id.usernameId);
        passwordEditText = findViewById(R.id.passwordId);
        loginButton = findViewById(R.id.loginButtonId);
        forgotPasswordTextView=findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        student_reference = FirebaseDatabase.getInstance().getReference("student_database");

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder =new AlertDialog.Builder(StudentLoginPage.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                builder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
                builder.setCancelable(false);
                builder.setTitle("Forgot Password?");
                builder.setMessage("Enter your email address");
                final AlertDialog alertDialog=builder.create();
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       email= edt.getText().toString();
                        student_reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                              for(DataSnapshot studentDeptSnapshot : dataSnapshot.getChildren()) {
                                    for (DataSnapshot studentBatchSnapshot : studentDeptSnapshot.getChildren()) {
                                        for (final DataSnapshot studentIDSnapshot : studentBatchSnapshot.getChildren()) {
                                            final StudentInfo student = studentIDSnapshot.getValue(StudentInfo.class);
                                            String newEmail= student.getEmail();
                                            if(newEmail.equals(email))
                                            {
                                                alertDialog.dismiss();
                                                final AlertDialog.Builder builder2 =new AlertDialog.Builder(StudentLoginPage.this);
                                                LayoutInflater inflater = getLayoutInflater();
                                                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                                                builder2.setView(dialogView);
                                                builder2.setCancelable(false);
                                                final EditText edt2 = (EditText) dialogView.findViewById(R.id.edit1);
                                                edt2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                builder2.setTitle("Forgot Password?");
                                                builder2.setMessage("Enter your new password");
                                                final AlertDialog alert=builder2.create();
                                                builder2.setPositiveButton("save", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                       String password= edt2.getText().toString();
                                                       studentIDSnapshot.getRef().child("password").setValue(password);
                                                       Toast.makeText(getApplicationContext(),"New password set successfully.",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                builder2.show();
                                                alert.dismiss();
                                                email="";
                                                break;
                                            }



                                        }

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                    }

                });
                builder.setNegativeButton("Back",null);

                builder.show();


            }
        });

        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {


                    loginButton.setTextColor(Color.BLACK);
                    loginButton.setBackground(getResources().getDrawable(R.drawable.pressed_button_shap));
                    final String username, password;
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
                    student_reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for(DataSnapshot studentDeptSnapshot : dataSnapshot.getChildren()) {
                                for (DataSnapshot studentBatchSnapshot : studentDeptSnapshot.getChildren()) {
                                    for (DataSnapshot studentIDSnapshot : studentBatchSnapshot.getChildren()) {
                                        StudentInfo student = studentIDSnapshot.getValue(StudentInfo.class);
                                        if (username.equals(student.getUsername()) && password.equals(student.getPassword())) {
                                            Intent intent = new Intent(StudentLoginPage.this, StudentPanel.class);
                                            intent.putExtra("stdid", student.getId());
                                            intent.putExtra("stdbatch", student.getBatch());
                                            startActivity(intent);
                                            Toast.makeText(StudentLoginPage.this, "login successfully", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }

                                    }

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(StudentLoginPage.this, "" + databaseError, Toast.LENGTH_SHORT).show();

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