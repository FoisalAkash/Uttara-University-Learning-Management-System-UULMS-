package com.sky.coderx.uulms.AdminPanel.AddStudent;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sky.coderx.uulms.SendEmailWithUsernamePassword.GMailSender;
import com.sky.coderx.uulms.R;

public class AddStudentActivity extends AppCompatActivity {

EditText idEditText,nameEditText,batchEditText,usernameEditText,passwordEditText,emailEditText,numberEditText;
Spinner deptSpinner;
Button saveButton;
String[] std_dept;
    ArrayAdapter<String> adapter;
    DatabaseReference reference;
    FirebaseAuth stdfirebaseAuth;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        getSupportActionBar().setTitle("Add Student Information");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(AddStudentActivity.this,R.color.DarkGreen));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2fc24a")));

        initview();
        initVariable();

    }

    private void initVariable() {
        reference = FirebaseDatabase.getInstance().getReference("student_database");
        stdfirebaseAuth=FirebaseAuth.getInstance();
    }

    private void initview() {
        nameEditText = findViewById(R.id.stdName);
        idEditText = findViewById(R.id.stdID);
        batchEditText = findViewById(R.id.stdBatch);
        usernameEditText = findViewById(R.id.stdUsername);
        passwordEditText = findViewById(R.id.stdPassword);
        emailEditText = findViewById(R.id.stdEmail);
        numberEditText = findViewById(R.id.stdMobileNumber);
        deptSpinner = findViewById(R.id.stdDept);
        saveButton = findViewById(R.id.saveButton);

        std_dept = getResources().getStringArray(R.array.dept);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, std_dept);
        deptSpinner.setAdapter(adapter);

    }


    @SuppressLint("ResourceAsColor")
    public void saveData(View view) {

        final String name, id, batch,dept,username,password,email,number;
        name = nameEditText.getText().toString().trim();
        id = idEditText.getText().toString().trim();
        batch = batchEditText.getText().toString().trim();
        username = usernameEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        number = numberEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        dept = deptSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            nameEditText.setError("Please Enter A Name");
            nameEditText.requestFocus();
            return;
        }
        if (id.isEmpty()) {
            idEditText.setError("Please Enter A ID");
            idEditText.requestFocus();
            return;
        }
 if (batch.isEmpty()) {
            batchEditText.setError("Please Enter A Batch");
            batchEditText.requestFocus();
            return;
        }
        if(dept.equals("Select A Department"))
        {
            TextView errorText = (TextView)deptSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Select A Department");
            deptSpinner.requestFocus();
            return;
        }
 if (username.isEmpty()) {
            usernameEditText.setError("Please Enter A username");
            usernameEditText.requestFocus();
            return;
        }
 if (password.isEmpty()) {
            passwordEditText.setError("Please Enter A password");
            passwordEditText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Please Enter A E-mail");
            emailEditText.requestFocus();
            return;
        }
 if (number.isEmpty()) {
            numberEditText.setError("Please Enter A Mobile Number");
            numberEditText.requestFocus();
            return;
        }
/////////////send mail start////////////////////
        new Thread(new Runnable() {


            public void run() {

                try {

                    GMailSender sender = new GMailSender("md.foisalahmed008@gmail.com","akashloveme");


                    sender.sendMail("UULMS(Student) Username & Password",
                            "Dear "+name+",\nYour Uttara University Learning Management System (UULMS) Student Login username = \""+username+"\" and password = \""+password+"\"",

                            "md.foisalahmed008@gmail.com",

                            email);









                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();



                }

            }

        }).start();

///////////////send mail end //////////////////////////
        reference.child(dept).child(batch).child(id).setValue(new StudentInfo(id, name,batch, dept,username,password,email,number)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {



                    Toast.makeText(AddStudentActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    String compareValue = "Select A Department";
                    int spinnerPosition = adapter.getPosition(compareValue);
                    deptSpinner.setSelection(spinnerPosition);





                    nameEditText.setText("");
                    idEditText.setText("");
                    batchEditText.setText("");
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    emailEditText.setText("");
                    numberEditText.setText("");

                } else {
                    Toast.makeText(AddStudentActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
