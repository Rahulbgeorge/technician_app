package com.example.rahul.technicial_side_app.Database;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

//return value not registerd, should be registerd in future,
//this method is called from dutymap page
public class uploadCustomersNewLocation implements AsyncResponse{

    public uploadCustomersNewLocation(LatLng loc,String bookingId)
    {
        new HttpReqeust()
                .addUrl("updateCustomerLocation")
                .addParameter("lat",loc.latitude)
                .addParameter("lng",loc.longitude)
                .post(this);
    }

    @Override
    public void postResponse(String response) {

        try{
            JSONObject obj=new JSONObject(response);
            if(obj.getString("result").equals("success"))
            {

            }
        }
        catch(Exception e)
        {}

    }
}
