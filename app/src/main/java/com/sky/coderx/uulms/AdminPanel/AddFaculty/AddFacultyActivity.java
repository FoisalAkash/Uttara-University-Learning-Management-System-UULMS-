package com.sky.coderx.uulms.AdminPanel.AddFaculty;

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
import com.sky.coderx.uulms.SendEmailWithUsernamePassword.GMailSender;

public class AddFacultyActivity extends AppCompatActivity {


    EditText fnameEditText,faddressEditText,femailEditText,fusernameEditText,fpasswordEditText,fnumberEditText,fdegreeEditText;
    Spinner fdeptSpinner;
    Button saveButton;
    String[] faculty_dept;
    ArrayAdapter<String> faculty_adapter;
    DatabaseReference faculty_reference;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        getSupportActionBar().setTitle("Faculty Information");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(AddFacultyActivity.this,R.color.DarkRed));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f7060a")));

        initview();
        initVariable();

    }

    private void initVariable() {
        faculty_reference = FirebaseDatabase.getInstance().getReference("faculty_database");
    }

    private void initview() {
        fnameEditText = findViewById(R.id.facultyName);
        femailEditText = findViewById(R.id.facultyEmail);
        faddressEditText = findViewById(R.id.facultyAddress);
        fusernameEditText = findViewById(R.id.facultyUsername);
        fpasswordEditText = findViewById(R.id.facultyPassword);
        fnumberEditText = findViewById(R.id.facultyMobileNumber);
        fdeptSpinner = findViewById(R.id.facultyDept);
        fdegreeEditText = findViewById(R.id.facultyDegree);
        saveButton = findViewById(R.id.saveButton);

        faculty_dept = getResources().getStringArray(R.array.dept);
        faculty_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, faculty_dept);
        fdeptSpinner.setAdapter(faculty_adapter);

    }


    public void saveData(View view) {

        final String name, email, address,dept,username,password,degree,number;
        name = fnameEditText.getText().toString().trim();
        email = femailEditText.getText().toString().trim();
        address = faddressEditText.getText().toString().trim();
        username = fusernameEditText.getText().toString().trim();
        password = fpasswordEditText.getText().toString().trim();
        degree = fdegreeEditText.getText().toString().trim();
        number = fnumberEditText.getText().toString().trim();
        dept = fdeptSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            fnameEditText.setError("Please Enter A Name");
            fnameEditText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            femailEditText.setError("Please Enter A ID");
            femailEditText.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            faddressEditText.setError("Please Enter A Batch");
            faddressEditText.requestFocus();
            return;
        }
        if(dept.equals("Select A Department"))
        {
            TextView errorText = (TextView)fdeptSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Select A Department");
            fdeptSpinner.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            fusernameEditText.setError("Please Enter A username");
            fusernameEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            fpasswordEditText.setError("Please Enter A password");
            fpasswordEditText.requestFocus();
            return;
        }
        if (degree.isEmpty()) {
            fdegreeEditText.setError("Please Enter A Degree");
            fdegreeEditText.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            fnumberEditText.setError("Please Enter A Mobile Number");
            fnumberEditText.requestFocus();
            return;
        }

        /////////////send mail start////////////////////
        new Thread(new Runnable() {


            public void run() {

                try {

                    GMailSender sender = new GMailSender("md.foisalahmed008@gmail.com","akashloveme");


                    sender.sendMail("UULMS(Faculty) Username & Password",
                            "Dear "+name+",\nYour Uttara University Learning Management System (UULMS) Faculty Login username = \""+username+"\" and password = \""+password+"\"",

                            "md.foisalahmed008@gmail.com",

                            email);









                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();



                }

            }

        }).start();

///////////////send mail end //////////////////////////



       final String user_id = faculty_reference.push().getKey();

        faculty_reference.child(dept).child(user_id).setValue(new FacultyInfo( name,email,address, dept,username,password,degree,number, user_id)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddFacultyActivity.this, "Saved", Toast.LENGTH_SHORT).show();


                    fnameEditText.setText("");
                    femailEditText.setText("");
                    faddressEditText.setText("");
                    fusernameEditText.setText("");
                    fpasswordEditText.setText("");
                    fdegreeEditText.setText("");
                    fnumberEditText.setText("");

                    String compareValue = "Select A Department";
                    int spinnerPosition = faculty_adapter.getPosition(compareValue);
                    fdeptSpinner.setSelection(spinnerPosition);
                } else {
                    Toast.makeText(AddFacultyActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
