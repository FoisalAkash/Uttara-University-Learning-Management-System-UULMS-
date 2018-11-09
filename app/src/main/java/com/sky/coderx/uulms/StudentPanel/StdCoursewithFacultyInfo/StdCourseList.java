package com.sky.coderx.uulms.StudentPanel.StdCoursewithFacultyInfo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sky.coderx.uulms.AdminPanel.AddFaculty.FacultyInfo;
import com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty.AssignCourseInfo;
import com.sky.coderx.uulms.R;

import java.util.ArrayList;
import java.util.List;

public class StdCourseList extends ArrayAdapter<AssignCourseInfo> {
    private Activity context;
    private List<AssignCourseInfo> assignedCourseList;
int myposition;
String fac,f;
DatabaseReference Ref=FirebaseDatabase.getInstance().getReference("faculty_database");

    public StdCourseList(Activity context, List<AssignCourseInfo> assignedCourseList) {
        super(context,R.layout.stdcourseview_list_layout,assignedCourseList);
        this.context=context;
        this.assignedCourseList=assignedCourseList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= context.getLayoutInflater();
        View rowItem=inflater.inflate(R.layout.stdcourseview_list_layout,null,true);

        TextView textViewName= rowItem.findViewById(R.id.stdCourseViewBatchName);
        final TextView textViewFaculty= rowItem.findViewById(R.id.stdCourseViewFacultyName);
        final TextView textViewFacultyNumber= rowItem.findViewById(R.id.stdCourseViewFacultyNumber);


        final AssignCourseInfo courseInfo=assignedCourseList.get(position);
        final String fid=courseInfo.getFaculty_id();

        Ref.addValueEventListener(new ValueEventListener() {
            ArrayList<String> sd=new ArrayList<String>();
            int c=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    for(DataSnapshot fdata:data.getChildren())
                    {
                        f=fdata.getKey();
                        if(fid.matches(f)) {
                            fac = fdata.getValue(FacultyInfo.class).getName();
                            String  nmbr=fdata.getValue(FacultyInfo.class).getMobile_number();
                            textViewFaculty.setText(Html.fromHtml("\u0009\u0009Faculty Name :<font color='#0822c9'> "+fac+"</font>"));
                            textViewFacultyNumber.setText(Html.fromHtml("\u0009\u0009Faculty Contact :<font color='#0822c9'> "+nmbr+"</font>"));
                            break;
                        }

                    }

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myposition=position+1;

        textViewName.setText(Html.fromHtml(myposition+". Course Name :<font color='#1f8f33'> "+courseInfo.getCourseName()+"</font>"));

        return rowItem;
    }
}
