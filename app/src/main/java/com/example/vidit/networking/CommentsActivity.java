package com.example.vidit.networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity
{
    ListView commentsListView;
    CommentsAdapter adapter;
    ArrayList<String> comments;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        intent=getIntent();
        commentsListView=findViewById(R.id.commentsListView);
        comments=intent.getStringArrayListExtra("Comments");
        adapter=new CommentsAdapter(this,comments);
        commentsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
