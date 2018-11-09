package com.sky.coderx.uulms.StudentPanel.StdFileView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sky.coderx.uulms.R;

import java.util.ArrayList;

public class StdViewFile extends AppCompatActivity {
RecyclerView recyclerView;String stduser_batch,batch;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_view_file);
        getSupportActionBar().setTitle("Study File");
        getWindow().setStatusBarColor(ContextCompat.getColor(StdViewFile.this,R.color.DarkCyan));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00838c")));



        stduser_batch= getIntent().getStringExtra("stdbatch");
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Upload_file_database");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot,String s) {
                for(DataSnapshot mdata:dataSnapshot.getChildren()) {
                    for (DataSnapshot data : mdata.getChildren()) {
                        batch = data.getKey();
                        for (DataSnapshot file : data.getChildren()) {

                            if (batch.equals(stduser_batch)) {
                                String fileName = file.getKey();
                                String url = file.getValue().toString();
                                ((MyAdapter) recyclerView.getAdapter()).update(fileName, url);
                            }
                        }
                    }

                }




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView=findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(StdViewFile.this));
        MyAdapter myAdapter=new MyAdapter(recyclerView,StdViewFile.this,new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);
    }
}
