package com.example.rahul.technicial_side_app.Database;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class fetchUserLocationData implements  AsyncResponse {
    AsyncFetchUserLocationData activity;
    public fetchUserLocationData(AsyncFetchUserLocationData activity, JSONArray array)
    {
        this.activity=activity;
        new HttpReqeust()
                .addUrl("/fetchUserLocationData/")
                .addParameter("data",array.toString())
                .post(this);
    }

    @Override
    public void postResponse(String response) {
        try{
            JSONObject obj=new JSONObject(response);
            JSONArray arr;
            Log.e("fetchout",obj.toString());
            Log.e("error for fetch",obj.getString("description"));
            if(obj.getString("result").equals("success"))
            {
                obj=(JSONObject) obj.get("description");
                this.activity.userlocationDataFetchComplete(obj);
            }

        }
        catch (Exception e){
            this.activity.userLocationDataFetchFail();
        }
    }
}
