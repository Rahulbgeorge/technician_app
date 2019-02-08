package com.example.rahul.technicial_side_app.Database;

import android.util.Log;

import com.example.rahul.technicial_side_app.DataTypes.User;

import org.json.JSONObject;

public class Login implements AsyncResponse {
    AsyncLoginResponse activity;
    String username,password;
    public Login(AsyncLoginResponse activity,String username,String password)
    {
        this.username=username;
        this.password=password;
        this.activity=activity;
        new HttpReqeust().addUrl("/loginTechnician/")
                .addParameter("username",username)
                .addParameter("password",password)
                .post(this);
    }


    @Override
    public void postResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.getString("result").equals("success"))
            {
                obj=(JSONObject)obj.get("description");
                String id=obj.getString("id");
                User user=new User(this.username,this.password,id);
                this.activity.userLoginSuccessful(user);
            }
            else
                this.activity.userLoginUnsuccessful();
        }
        catch (Exception e)
        {
            Log.e("Login error","error occured during login");
            e.printStackTrace();
        }
    }
}
