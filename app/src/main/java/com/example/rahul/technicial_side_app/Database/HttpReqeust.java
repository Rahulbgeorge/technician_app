package com.example.rahul.technicial_side_app.Database;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpReqeust {
    String url="http://34.73.62.26";
//    String url="http://192.168.43.2";
    String data;
    Map<String,Object> params;
    Activity activity;
    AsyncResponse responseActivity;


        public HttpReqeust()
        {
             params= new LinkedHashMap<>();
        }

        public HttpReqeust addParameter(String key,String value)
        {
            params.put(key,value);
            return this;
        }

        public HttpReqeust addParameter(String key, Object value)
        {
            params.put(key,value);
            return this;
        }

        public HttpReqeust addUrl(String url)
        {
            this.url=this.url+url;
            return this;
        }



        public void post(AsyncResponse activity)
        {
            responseActivity=activity;
            new Downloader().execute();
        }

        private String makePost()  {

        StringBuilder postData = new StringBuilder();
        try {
            Log.e("Downloadinig",this.url);
            URL url = new URL(this.url);

            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();
            Log.e("HTTP Request", response);
            return response;
        }
        catch(Exception e)
        {
            Log.e("HttpRequest","exception occured"+e.getMessage());

        }
        return "{\"result\":\"fail\",\"description\":\"Internal error\"}";
        }

        class Downloader extends AsyncTask<Void,String,String>
        {
            @Override
            protected String doInBackground(Void... voids) {
                String out=makePost();
                return out;
            }

            @Override
            protected void onPostExecute(String response) {
                responseActivity.postResponse(response);
                }
        }



}



