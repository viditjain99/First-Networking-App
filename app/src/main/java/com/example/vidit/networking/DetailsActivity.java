package com.example.vidit.networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView titleTextView,bodyTextView,nameTextView,emailTextView;
    ProgressBar progressBar;
    Intent intent,intent1;
    ArrayList<String> details;
    ArrayList<String> comments;
    public static int userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        titleTextView=findViewById(R.id.titleTextView);
        bodyTextView=findViewById(R.id.bodyTextView);
        nameTextView=findViewById(R.id.nameTextView);
        emailTextView=findViewById(R.id.emailTextView);
        progressBar=findViewById(R.id.progressBar);
        intent=getIntent();
        details=new ArrayList<>();
        details=intent.getStringArrayListExtra("Details");
        titleTextView.setText(details.get(0));
        bodyTextView.setText(details.get(1));
        nameTextView.setText(details.get(2));
        emailTextView.setText(details.get(3));
        userId=Integer.parseInt(details.get(4));
        comments=new ArrayList<>();
        nameTextView.setOnClickListener(DetailsActivity.this);
    }

    @Override
    public void onClick(View view)
    {
        int id=view.getId();
        if(id==R.id.nameTextView)
        {
            progressBar.setVisibility(View.VISIBLE);
            titleTextView.setVisibility(View.GONE);
            bodyTextView.setVisibility(View.GONE);
            nameTextView.setVisibility(View.GONE);
            emailTextView.setVisibility(View.GONE);
            intent1=new Intent(DetailsActivity.this,CommentsActivity.class);
            PostAsyncTaskComments task=new PostAsyncTaskComments(new PostDownloadListener()
            {
                @Override
                public void onDownload(ArrayList<String> commentOb)
                {
                    comments.clear();
                    comments.addAll(commentOb);
                    progressBar.setVisibility(View.GONE);
                    intent1.putExtra("Comments",comments);
                    startActivity(intent1);
                }
            });
            task.execute("https://jsonplaceholder.typicode.com/users/"+userId+""+"/posts");
        }
    }
}
