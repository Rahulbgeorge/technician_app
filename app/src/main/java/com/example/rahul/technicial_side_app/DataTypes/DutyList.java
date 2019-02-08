package com.example.rahul.technicial_side_app.DataTypes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DutyList implements Serializable {

    @SerializedName("return")
     Duties duties;
     Context context;
    public DutyList(ArrayList<Customer> duty,Context context)
    {this.duties=new Duties(duty);
    this.context=context;}

    public DutyList()
    {this.duties=null;}

    public void loadDutyToLocalMemory()
    {
        SharedPreferences.Editor editor=this.context.getSharedPreferences("ion_exchange",Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.duties);
        Log.e("Serialized duty",json);
        editor.putString("duty",json);
    }

    private Duties loadDutyFromMemory()
    {
        SharedPreferences pref=this.context.getSharedPreferences("ion_exchange",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=this.context.getSharedPreferences("ion_exchange",Context.MODE_PRIVATE).edit();
        Gson gson=new Gson();
        String jsonData=pref.getString("duty","");
        Log.e("recieved Json",jsonData+" found");

        return gson.fromJson(jsonData,Duties.class);

    }

    public ArrayList<Customer> getDuty()
    {
        if(this.duties==null)
        {
            this.duties=loadDutyFromMemory();
            return this.duties.duty;
        }
        return this.duties.duty;
    }
}

class Duties implements Serializable{
    @SerializedName("return")
    ArrayList<Customer> duty;
    public Duties(ArrayList<Customer> duty)
    {this.duty=duty;
    }


}
