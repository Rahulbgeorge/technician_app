package com.example.rahul.technicial_side_app.DataTypes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable {
String username,password,id;

    public User(String username, String password, String id){
        this.username=username;
        this.password=password;
        this.id=id;

    }

    public User()
    {
    }

    public User loadUserFromMemory(Context context)
    {
        SharedPreferences pref=context.getSharedPreferences("technician_side_app",Context.MODE_PRIVATE);
        String jsonuser=pref.getString("jsonUser","");
        Gson gson=new Gson();
        User user=gson.fromJson(jsonuser,User.class);
        return user;
    }

    public String getUsername()
    {return this.username;}

    public String getPassword()
    {return this.password; }

    public String getId()
    {return this.id;}

}
