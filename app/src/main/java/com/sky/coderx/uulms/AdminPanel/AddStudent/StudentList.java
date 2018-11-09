package com.sky.coderx.uulms.AdminPanel.AddStudent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.coderx.uulms.R;

import java.util.List;

public class StudentList extends ArrayAdapter<StudentInfo> {
    private Activity context;
    private List<StudentInfo> sstdList;
int myposition;

    public StudentList(Activity context, List<StudentInfo> sstdList) {
        super(context, R.layout.stdview_list_layout,sstdList);
        this.context=context;
        this.sstdList=sstdList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= context.getLayoutInflater();
        View rowItem=inflater.inflate(R.layout.stdview_list_layout,null,true);

        TextView textViewName= rowItem.findViewById(R.id.stdViewName);
        TextView textViewId= rowItem.findViewById(R.id.stdViewId);
        TextView textViewBatch= rowItem.findViewById(R.id.stdViewBatch);
        TextView textViewDept= rowItem.findViewById(R.id.stdViewDept);

        final StudentInfo studentInfo=sstdList.get(position);
        myposition=position+1;

        textViewName.setText(myposition+". "+studentInfo.getName());
        textViewId.setText("        ID   : "+studentInfo.getId());
        textViewBatch.setText("        Batch: "+studentInfo.getBatch());
        textViewDept.setText(Html.fromHtml("\u0009\u0009Department: <font color='#0205b1'>"+studentInfo.getDept()+"</font>"));


        /*rowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),""+studentInfo.getName(),Toast.LENGTH_SHORT).show();


            }
        });
*/
        return rowItem;
    }
}
