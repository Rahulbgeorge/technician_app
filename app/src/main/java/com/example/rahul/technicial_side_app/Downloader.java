package com.example.rahul.technicial_side_app;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Priyanka on 7/11/17.
 */

public class Downloader {

    public String readUrl(String myUrl) throws IOException
    {
        Log.e("datadownloader","recieved url to download"+myUrl);
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            Log.e("datadownload","attempting connection");
            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while((line = br.readLine()) != null)
            {
                sb.append(line);

            }

            data = sb.toString();
            Log.d("datadownload", data.toString()+"is the downloaded output");

            br.close();

        } catch (MalformedURLException e) {
            Log.e("datadownload",e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("datadownload",e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(inputStream != null)
                inputStream.close();
            if(urlConnection!=null)
            urlConnection.disconnect();
        }

        Log.e("dataDownloadOO",data);
        return data;

    }




}