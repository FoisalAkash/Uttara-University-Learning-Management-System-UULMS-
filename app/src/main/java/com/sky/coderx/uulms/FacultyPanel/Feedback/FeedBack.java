package com.sky.coderx.uulms.FacultyPanel.Feedback;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
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
import com.sky.coderx.uulms.R;
import com.sky.coderx.uulms.SendEmailWithUsernamePassword.GMailSender;

public class FeedBack extends AppCompatActivity {
EditText feedbackEmail,feedbackPassword,feedbackMsg;
Button feedbackBtn;
DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        getSupportActionBar().setTitle("Feedback");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        feedbackEmail=findViewById(R.id.feedbackEmail);
        feedbackPassword=findViewById(R.id.feedbackPassword);
        feedbackMsg=findViewById(R.id.feedbackMsg);
        feedbackBtn=findViewById(R.id.feedbackButton);

        reference = FirebaseDatabase.getInstance().getReference("feedback");
    }

    public void feedbackData(View view) {

        final String msg,password,email;
        msg = feedbackMsg.getText().toString().trim();
        password = feedbackPassword.getText().toString().trim();
        email = feedbackEmail.getText().toString().trim();


        if (email.isEmpty()) {
            feedbackEmail.setError("Please Enter Your E-mail");
            feedbackEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            feedbackPassword.setError("Please Your E-mai Password");
            feedbackPassword.requestFocus();
            return;
        }
        if (msg.isEmpty()) {
            feedbackMsg.setError("Please Enter Your Message");
            feedbackMsg.requestFocus();
            return;
        }

/////////////send mail start////////////////////
        new Thread(new Runnable() {


            public void run() {

                try {

                    GMailSender sender = new GMailSender(feedbackEmail.getText().toString(),feedbackPassword.getText().toString());


                    sender.sendMail("UULMS Faculty Feedback","Dear Admin,\n"+msg,email,"md.foisalahmed008@gmail.com");


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();



                }

            }

        }).start();

///////////////send mail end //////////////////////////
        String id=reference.push().getKey();
        reference.child("faculty_feedback").child(id).setValue(new FeedbackInfo(email,msg)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {



                    Toast.makeText(FeedBack.this, "Send E-mail Successfully!", Toast.LENGTH_SHORT).show();


                    feedbackEmail.setText("");
                    feedbackPassword.setText("");
                    feedbackMsg.setText("");

                } else {
                    Toast.makeText(FeedBack.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
