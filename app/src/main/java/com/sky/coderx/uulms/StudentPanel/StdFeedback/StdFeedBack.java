package com.sky.coderx.uulms.StudentPanel.StdFeedback;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sky.coderx.uulms.FacultyPanel.Feedback.FeedbackInfo;
import com.sky.coderx.uulms.R;
import com.sky.coderx.uulms.SendEmailWithUsernamePassword.GMailSender;

public class StdFeedBack extends AppCompatActivity {
    EditText stdfeedbackEmail,stdfeedbackPassword,stdfeedbackMsg;
    Button stdfeedbackBtn;
    DatabaseReference reference;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_feed_back);
        getSupportActionBar().setTitle("Feedback");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(StdFeedBack.this,R.color.DarkCerise));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#df0091")));

        stdfeedbackEmail=findViewById(R.id.stdfeedbackEmail);
        stdfeedbackPassword=findViewById(R.id.stdfeedbackPassword);
        stdfeedbackMsg=findViewById(R.id.stdfeedbackMsg);
        stdfeedbackBtn=findViewById(R.id.stdfeedbackButton);

        reference = FirebaseDatabase.getInstance().getReference("feedback");
    }

    public void stdfeedbackData(View view) {

        final String msg,password,email;
        msg = stdfeedbackMsg.getText().toString().trim();
        password = stdfeedbackPassword.getText().toString().trim();
        email = stdfeedbackEmail.getText().toString().trim();


        if (email.isEmpty()) {
            stdfeedbackEmail.setError("Please Enter Your E-mail");
            stdfeedbackEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            stdfeedbackPassword.setError("Please Your E-mai Password");
            stdfeedbackPassword.requestFocus();
            return;
        }
        if (msg.isEmpty()) {
            stdfeedbackMsg.setError("Please Enter Your Message");
            stdfeedbackMsg.requestFocus();
            return;
        }

/////////////send mail start////////////////////
        new Thread(new Runnable() {


            public void run() {

                try {

                    GMailSender sender = new GMailSender(stdfeedbackEmail.getText().toString(),stdfeedbackPassword.getText().toString());


                    sender.sendMail("UULMS Student Feedback","Dear Admin,\n"+msg,email,"md.foisalahmed008@gmail.com");


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();



                }

            }

        }).start();

///////////////send mail end //////////////////////////
        String id=reference.push().getKey();
        reference.child("student_feedback").child(id).setValue(new FeedbackInfo(email,msg)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {



                    Toast.makeText(StdFeedBack.this, "Send E-mail Successfully!", Toast.LENGTH_SHORT).show();


                    stdfeedbackEmail.setText("");
                    stdfeedbackPassword.setText("");
                    stdfeedbackMsg.setText("");

                } else {
                    Toast.makeText(StdFeedBack.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
