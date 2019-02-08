package com.example.rahul.technicial_side_app.Database;

import android.util.Log;

import com.example.rahul.technicial_side_app.DataTypes.Customer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadDutyInformation implements AsyncResponse {

    AsyncTodaysDutyResponse activity;
    public DownloadDutyInformation(AsyncTodaysDutyResponse activity,String technician_id)
    {
        this.activity=activity;
        new HttpReqeust().addUrl("/displayTodaysBooking/")
                .addParameter("technician_id",technician_id)
                .post(this);


    }

    @Override
    public void postResponse(String response) {
        try{
            JSONObject obj=new JSONObject(response);
            if(obj.getString("result").equals("success"))
            {
                ArrayList<Customer> customers=new ArrayList<>();
                JSONArray array=(JSONArray)obj.get("description");
                String phone,device,pincode,area,clientname,alternate_phone,deviceId,problem;
                double lat,lng;
                for(int i=0;i<array.length();i++)
                {

                    Log.e("device","no"+i);
                    obj= array.getJSONObject(i);
                    Log.e("device","no"+i);

                    device=(String)obj.get("deviceName");
                    Log.e("device","no"+i);

                    pincode=obj.getString("pincode");
                    Log.e("device","no"+i);

                    area=obj.getString("area");

                    clientname=obj.getString("client_name");

                    lat=obj.getDouble("lat");

                    lng=obj.getDouble("lng");

                    phone=obj.getString("phone");

                    alternate_phone=obj.getString("alternate_phone");

                    deviceId=obj.getString("deviceId");

                    problem=obj.getString("problem");
                    String id=obj.getString("id");
                    customers.add(new Customer(clientname,area,phone,lat,lng,alternate_phone,device,deviceId,problem,id));
                }



                this.activity.todaysDutyDownloadedSuccessfully(customers);
            }
            else
            {
                this.activity.todaysDutyDownloadFailed("Oops something went wrong");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.e("duty parsse error","error is"+e.getMessage());
            this.activity.todaysDutyDownloadFailed("oops server error occured");
        }
    }

}
