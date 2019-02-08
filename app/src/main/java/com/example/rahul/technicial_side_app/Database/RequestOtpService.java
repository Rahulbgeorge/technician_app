package com.example.rahul.technicial_side_app.Database;

import android.util.Log;

import org.json.JSONObject;

public class RequestOtpService implements AsyncResponse {
    AsyncRequestOtp activity;
    public RequestOtpService(AsyncRequestOtp activity,String phone)
    {
        this.activity=activity;
        Log.e("Posted phone",phone);
        new HttpReqeust().addUrl("/sendUserVerificationOtp/")
                .addParameter("mobile",phone+"")
                .post(this);
    }

    @Override
    public void postResponse(String response) {
        try{
            JSONObject obj=new JSONObject(response);
            if(obj.getString("result").equals("success"))
            {
                String otp=obj.getString("description");
                this.activity.OtpPostSuccessful(otp);

            }
        }
        catch(Exception e){}
    }
}
