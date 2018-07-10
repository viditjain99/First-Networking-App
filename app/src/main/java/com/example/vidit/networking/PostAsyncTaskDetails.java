package com.example.vidit.networking;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class PostAsyncTaskDetails extends AsyncTask<String,Void,ArrayList<String>> {

    PostDownloadListener listener;
    ArrayList<String> details;
    PostAsyncTaskDetails(PostDownloadListener listener)
    {
        this.listener=listener;
    }
    @Override
    protected ArrayList<String> doInBackground(String... strings)
    {
        details=new ArrayList<>();
        String urlString1=strings[0];
        String urlString2=strings[1];
        int userId=1;
        try
        {
            URL url1=new URL(urlString1);
            HttpsURLConnection urlConnection1=(HttpsURLConnection) url1.openConnection();
            urlConnection1.setRequestMethod("GET");
            urlConnection1.connect();
            InputStream inputStream=urlConnection1.getInputStream();
            Scanner scanner=new Scanner(inputStream);
            String result1="";
            while(scanner.hasNext())
            {
                result1=result1+scanner.next();
            }
            JSONArray rootArray=new JSONArray(result1);
            for(int i=0;i<rootArray.length();i++)
            {
                JSONObject postObject=rootArray.getJSONObject(i);
                int id=postObject.getInt("id");
                if(id==MainActivity.position)
                {
                    String title=postObject.getString("title");
                    String body=postObject.getString("body");
                    userId=postObject.getInt("userId");
                    details.add(title);
                    details.add(body);
                    break;
                }
            }
            URL url2=new URL(urlString2);
            HttpsURLConnection urlConnection2=(HttpsURLConnection) url2.openConnection();
            urlConnection2.setRequestMethod("GET");
            urlConnection2.connect();
            InputStream inputStream1=urlConnection2.getInputStream();
            Scanner scanner1=new Scanner(inputStream1);
            String result2="";
            while(scanner1.hasNext())
            {
                result2=result2+scanner1.next();
            }
            Log.d("result2",result2);
            JSONArray rootArray1=new JSONArray(result2);
            for(int i=0;i<rootArray1.length();i++)
            {
                JSONObject postObject=rootArray1.getJSONObject(i);
                int usrId=postObject.getInt("id");
                if(usrId==userId)
                {
                    String name=postObject.getString("name");
                    String email=postObject.getString("email");
                    details.add(name);
                    details.add(email);
                    details.add(userId+"");
                    break;
                }
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return details;
    }
    public void onPostExecute(ArrayList<String> strings)
    {
        super.onPostExecute(strings);
        listener.onDownload(strings);
    }
}
