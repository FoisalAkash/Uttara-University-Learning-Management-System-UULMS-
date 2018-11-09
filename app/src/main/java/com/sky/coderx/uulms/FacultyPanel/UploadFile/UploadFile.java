package com.sky.coderx.uulms.FacultyPanel.UploadFile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty.AssignCourseInfo;
import com.sky.coderx.uulms.R;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

public class UploadFile extends AppCompatActivity {

    Button selectFilebtn,uploadbtn;
    TextView fileNameTextView;

    DatabaseReference assignedCourseRef;
    Spinner deptSpinner,coursrSpinner,batchSpinner;
    String selectedBatch,selectedCourse,selectedDept;
    ArrayAdapter<String> batchSelect;
    ArrayAdapter<String> courseSelect;
    ArrayAdapter<String> deptSelect;
int position=1;

    String result;
FirebaseDatabase database;
FirebaseStorage storage;
Uri fileUri;
ProgressDialog progressDialog;
String facultyuser_id,facultyuser_dept;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        getSupportActionBar().setTitle("Upload File");

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.DarkPurpale));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bd00d6")));
        assignedCourseRef=FirebaseDatabase.getInstance().getReference("AssignCourse_database");

        facultyuser_id= getIntent().getStringExtra("fid");
         facultyuser_dept= getIntent().getStringExtra("fdept");

        coursrSpinner=findViewById(R.id.ACourse);
        batchSpinner=findViewById(R.id.ABatch);
        deptSpinner=findViewById(R.id.ADept);
        fileNameTextView=findViewById(R.id.fileNameText);
        selectFilebtn=findViewById(R.id.selectFile);
        uploadbtn=findViewById(R.id.uploadButton);


storage=FirebaseStorage.getInstance();
database=FirebaseDatabase.getInstance();


selectFilebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(ContextCompat.checkSelfPermission(UploadFile.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            selectFile();
        }
        else
        {
            ActivityCompat.requestPermissions(UploadFile.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
        }
    }
});

uploadbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(fileUri!=null)
        {

            if(selectedBatch.equals("---Select a batch---")||selectedCourse.equals("---Select a course---"))
            {
                Toast.makeText(getApplicationContext(),"select a course & batch",Toast.LENGTH_SHORT).show();
            }
            else
            {

                    uploadMyFile(fileUri);



            }


        }
        else
        {
            Toast.makeText(getApplicationContext(),"select a file",Toast.LENGTH_SHORT).show();
        }
    }
});


    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final ArrayList<String> dept = new ArrayList<>();
        assignedCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dept.clear();
                dept.add("---Select a dept---");
                for(DataSnapshot studentdeptSnapshot : dataSnapshot.getChildren()) {
                    String e = studentdeptSnapshot.getKey();
                    Log.e("d",e);
                    if(e.equals(facultyuser_dept))
                    {
                        coursrSpinner.setEnabled(true);
                        dept.add(e);
                    }
                }


                deptSelect = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_item, dept);
                deptSpinner.setAdapter(deptSelect);
                deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedDept=deptSpinner.getSelectedItem().toString();
                        deptm();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void deptm()
    {
        final ArrayList<String> course = new ArrayList<>();
        assignedCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                course.clear();

                course.add("---Select a course---");
                for(DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    String c=Snapshot.getKey();
                    for (DataSnapshot studentBatchSnapshot : Snapshot.getChildren()) {

                        if (selectedDept.equals(c)) {
                            batchSpinner.setEnabled(true);

                            String d = studentBatchSnapshot.getValue(AssignCourseInfo.class).getCourseName();
                            course.add(d);
                        }
                    }
                }


                courseSelect = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_item, course);
                coursrSpinner.setAdapter(courseSelect);
                coursrSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCourse=coursrSpinner.getSelectedItem().toString();
                        batchm();



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void batchm() {
        final ArrayList<String> batch = new ArrayList<>();
        assignedCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                batch.clear();
                batch.add("---Select a batch---");
                for(DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot studentBatchSnapshot : studentSnapshot.getChildren()) {


                        String b = studentBatchSnapshot.getValue(AssignCourseInfo.class).getCourseName();
                        if (selectedCourse.equals(b)) {
                            batchSpinner.setEnabled(true);

                            String d = studentBatchSnapshot.getValue(AssignCourseInfo.class).getBatchNumber();
                            batch.add(d);
                        }
                    }
                }


                batchSelect = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_item, batch);
                batchSpinner.setAdapter(batchSelect);
                batchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch=batchSpinner.getSelectedItem().toString();




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void uploadMyFile(Uri fileUri) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file.....");
        progressDialog.setProgress(0);

        if (result == null) {
            result = fileUri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        String fileNameWithOutExt = FilenameUtils.removeExtension(result);
        Log.e("fw",fileNameWithOutExt);
        final String fileName=fileNameWithOutExt+".pdf";

        final String fileName1=fileNameWithOutExt+"";
        StorageReference storageReference=storage.getReference();

        storageReference.child(selectedDept).child(selectedCourse).child(selectedBatch).child(fileName).putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String uri=taskSnapshot.getDownloadUrl().toString();
                        DatabaseReference reference=database.getReference("Upload_file_database");

                            reference.child(selectedDept).child(selectedCourse).child(selectedBatch).child(fileName1).setValue(uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {

                                        progressDialog.dismiss();
                                        fileNameTextView.setText("No File Is Selected");


                                        int spinnerPosition =0;
                                        deptSpinner.setSelection(spinnerPosition);
                                        coursrSpinner.setSelection(spinnerPosition);
                                        batchSpinner.setSelection(spinnerPosition);

                                        Toast.makeText(getApplicationContext(),"File successfully uploaded",Toast.LENGTH_SHORT).show();
                                        position++;
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"File Not successfully uploaded",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"File upload Error",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.show();
                int currentProgress= (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectFile();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"please provide permission..",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFile() {

        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==86 && resultCode==RESULT_OK && data!=null)
        {
            fileUri=data.getData();
            fileNameTextView.setText("A file is selected :"+data.getData().getLastPathSegment());
        }
        else
        {
            Toast.makeText(getApplicationContext(),"please select a file",Toast.LENGTH_SHORT).show();
        }
    }

}
