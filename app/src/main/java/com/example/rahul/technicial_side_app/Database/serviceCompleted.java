package com.example.rahul.technicial_side_app.Database;

import org.json.JSONObject;

public class serviceCompleted implements AsyncResponse {
    AsyncServiceCompleted activity;
    public serviceCompleted(AsyncServiceCompleted activity,String payment,String bookingId)
    {
        this.activity=activity;
        new HttpReqeust()
        .addParameter("cost",Double.parseDouble(payment))
        .addParameter("serviceId",bookingId)
                .post(this);
    }
    @Override
    public void postResponse(String response) {
        try{
            JSONObject obj=new JSONObject(response);
            if(obj.getString("result").equals("success"))
            {
                this.activity.serivceUpdateSuccessful();
            }
            else
            {
                this.activity.serviceUpdateFailure();
            }
        }
        catch (Exception e){}

    }
}
