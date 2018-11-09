package com.sky.coderx.uulms.StudentPanel.StdFileView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.coderx.uulms.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
RecyclerView recycler;
Context context;
ArrayList<String> items = new ArrayList<String>();
ArrayList<String> urls = new ArrayList<String>();

public void update(String fileName,String url)
{
    items.add(fileName);
    urls.add(url);
    notifyDataSetChanged();
}

    public MyAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls) {
        this.recycler = recyclerView;
        this.context = context;
        this.items = items;
        this.urls = urls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nameOfFile.setText(items.get(i)+".pdf");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfFile;

        public ViewHolder(View fileitemView) {
            super(fileitemView);
            nameOfFile=fileitemView.findViewById(R.id.myTextFromCardView);
            fileitemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int position=recycler.getChildLayoutPosition(view);
                    Intent fileintent=new Intent(Intent.ACTION_QUICK_VIEW,Uri.parse(urls.get(position)));
                    context.startActivity(fileintent);
                    Toast.makeText(context,"please wait....",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}

