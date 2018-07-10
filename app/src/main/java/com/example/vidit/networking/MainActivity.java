package com.example.vidit.networking;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;
    ArrayList<String> posts;
    ArrayList<String> details;
    public static int position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        progressBar=findViewById(R.id.progressBar);
        posts=new ArrayList<>();
        details=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,posts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    public void fetchData(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        PostAsyncTask task=new PostAsyncTask(new PostDownloadListener()
        {
            @Override
            public void onDownload(ArrayList<String> titles)
            {
                posts.clear();
                posts.addAll(titles);
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });
        task.execute("https://jsonplaceholder.typicode.com/posts");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        final Intent intent=new Intent(this,DetailsActivity.class);
        position=i+1;
        PostAsyncTaskDetails task=new PostAsyncTaskDetails(new PostDownloadListener() {
            @Override
            public void onDownload(ArrayList<String> strings)
            {
                details.clear();
                details.addAll(strings);
                progressBar.setVisibility(View.GONE);
                intent.putExtra("Details",details);
                startActivity(intent);
            }
        });
        task.execute("https://jsonplaceholder.typicode.com/posts","https://jsonplaceholder.typicode.com/users");
    }
}
