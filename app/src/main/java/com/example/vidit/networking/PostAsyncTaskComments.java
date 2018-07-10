package com.example.vidit.networking;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class PostAsyncTaskComments extends AsyncTask<String,Void,ArrayList<String>>
{

    PostDownloadListener listener;
    ArrayList<String> comments;
    PostAsyncTaskComments(PostDownloadListener listener)
    {
        this.listener=listener;
    }
    @Override
    protected ArrayList<String> doInBackground(String... strings)
    {
        comments=new ArrayList<>();
        String urlString=strings[0];
        try
        {
            URL url=new URL(urlString);
            HttpsURLConnection urlConnection=(HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            Scanner scanner=new Scanner(inputStream);
            String result="";
            while (scanner.hasNext()) {
                result=result+scanner.next();
            }
            JSONArray rootArray=new JSONArray(result);
            for (int i=0;i<rootArray.length();i++) {
                JSONObject commentObject=rootArray.getJSONObject(i);
                String title=commentObject.getString("title");
                String body=commentObject.getString("body");
                comments.add(title);
                comments.add(body);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
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
        return comments;
    }
    public void onPostExecute(ArrayList<String> comments)
    {
        super.onPostExecute(comments);
        listener.onDownload(comments);
    }
}
