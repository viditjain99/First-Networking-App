package com.example.vidit.networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentsAdapter extends ArrayAdapter
{
    ArrayList<String> comments;
    LayoutInflater inflater;
    int i=0;
    public CommentsAdapter(Context context,ArrayList<String> comments)
    {
        super(context,0,comments);
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.comments=comments;
    }
    @Override
    public int getCount()
    {
        return comments.size()/2;
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View output=convertView;
        if(output==null)
        {
            output=inflater.inflate(R.layout.row_layout,parent,false);
            TextView titleTextView=output.findViewById(R.id.titleTextView);
            TextView bodyTextView=output.findViewById(R.id.bodyTextView);
            CommentViewHolder viewHolder=new CommentViewHolder();
            viewHolder.title=titleTextView;
            viewHolder.body=bodyTextView;
            output.setTag(viewHolder);
        }
        CommentViewHolder viewHolder=(CommentViewHolder) output.getTag();
        if(i<comments.size())
        {
            viewHolder.title.setText(comments.get(i));
            i=i+1;
            viewHolder.body.setText(comments.get(i));
            i=i+1;
        }
        return output;
    }
}
