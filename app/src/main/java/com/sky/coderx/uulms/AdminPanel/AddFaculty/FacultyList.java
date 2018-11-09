package com.sky.coderx.uulms.AdminPanel.AddFaculty;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sky.coderx.uulms.AdminPanel.AddStudent.StudentInfo;
import com.sky.coderx.uulms.R;

import java.util.List;

import static com.sky.coderx.uulms.R.layout.facultyview_list_layout;

public class FacultyList extends ArrayAdapter<FacultyInfo> {
    private Activity context;
    private List<FacultyInfo> facultyList;
int myposition;

    public FacultyList(Activity context, List<FacultyInfo> facultyList) {
        super(context,R.layout.facultyview_list_layout,facultyList);
        this.context=context;
        this.facultyList=facultyList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= context.getLayoutInflater();
        View rowItem=inflater.inflate(R.layout.facultyview_list_layout,null,true);

        TextView textViewName= rowItem.findViewById(R.id.facultyViewName);
        TextView textViewId= rowItem.findViewById(R.id.facultyViewEmail);
        TextView textViewContact= rowItem.findViewById(R.id.facultyViewContact);
        TextView textViewDept= rowItem.findViewById(R.id.facultyViewDept);

        final FacultyInfo facultyInfo=facultyList.get(position);
        myposition=position+1;

        textViewName.setText(myposition+". "+facultyInfo.getName());
        textViewId.setText("        E-mail   : "+facultyInfo.getEmail());
        textViewContact.setText("        Contact Number: "+facultyInfo.getMobile_number());
        textViewDept.setText(Html.fromHtml("\u0009\u0009Department: <font color='#0205b1'>"+facultyInfo.getDept()+"</font>"));


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
