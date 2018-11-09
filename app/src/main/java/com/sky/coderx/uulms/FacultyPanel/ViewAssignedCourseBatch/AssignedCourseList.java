package com.sky.coderx.uulms.FacultyPanel.ViewAssignedCourseBatch;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sky.coderx.uulms.AdminPanel.AddFaculty.FacultyInfo;
import com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty.AssignCourseInfo;
import com.sky.coderx.uulms.R;

import java.util.List;

public class AssignedCourseList extends ArrayAdapter<AssignCourseInfo> {
    private Activity context;
    private List<AssignCourseInfo> assignedCourseList;
int myposition;

    public AssignedCourseList(Activity context, List<AssignCourseInfo> assignedCourseList) {
        super(context,R.layout.assignedcourseview_list_layout,assignedCourseList);
        this.context=context;
        this.assignedCourseList=assignedCourseList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= context.getLayoutInflater();
        View rowItem=inflater.inflate(R.layout.assignedcourseview_list_layout,null,true);

        TextView textViewName= rowItem.findViewById(R.id.assignedCourseViewName);
        TextView textViewId= rowItem.findViewById(R.id.assignedBatchViewName);

        final AssignCourseInfo courseInfo=assignedCourseList.get(position);
        myposition=position+1;

        textViewName.setText(Html.fromHtml(myposition+". Course Name :<font color='#1f8f33'> "+courseInfo.getCourseName()+"</font>"));
        textViewId.setText(Html.fromHtml("\u0009"+"\u0009"+"Batch Number:<font color='blue'> "+courseInfo.getBatchNumber()+"</font>"));

        return rowItem;
    }
}
