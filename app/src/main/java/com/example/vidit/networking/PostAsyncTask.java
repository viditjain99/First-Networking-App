package com.example.vidit.networking;

import android.os.AsyncTask;

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

public class PostAsyncTask extends AsyncTask<String,Void,ArrayList<String>>
{

    PostDownloadListener listener;
    PostAsyncTask(PostDownloadListener listener)
    {
        this.listener=listener;
    }
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> ids=new ArrayList<>();
        String urlString=strings[0];
        try {
            URL url=new URL(urlString);
            HttpsURLConnection urlConnection=(HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            Scanner scanner=new Scanner(inputStream);
            String result="";
            while(scanner.hasNext())
            {
                result=result+scanner.next();
            }
            JSONArray rootArray=new JSONArray(result);
            for(int i=0;i<rootArray.length();i++)
            {
                JSONObject postObject=rootArray.getJSONObject(i);
                String id=postObject.getString("id");
                id="Post "+id;
                ids.add(id);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public void onPostExecute(ArrayList<String> strings)
    {
        super.onPostExecute(strings);
        listener.onDownload(strings);
    }
}