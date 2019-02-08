package com.example.rahul.technicial_side_app.tools;

import org.json.JSONArray;
import org.json.JSONObject;

public class google_distance_parser {
    String distance;
    String time;
    String googleResponseJson;
    public google_distance_parser(String jsondata){googleResponseJson=jsondata;}

    public void addDistanceParser(String jsondata)
    {googleResponseJson=jsondata;}

    private void parseDistanace()
    {
        try{
            JSONObject obj=new JSONObject(googleResponseJson);
            JSONArray arr=(JSONArray) obj.get("routes");
            obj=(JSONObject)arr.get(0);
            arr=(JSONArray)obj.get("legs");
            obj=(JSONObject) arr.get(0);
            obj=(JSONObject)obj.get("distance");
            String distance=obj.getString("text");
            this.distance=distance;
        }
        catch (Exception e){
            this.distance="parsing error";
        }
    }

    private void parseTime()
    {
        try{
            JSONObject obj=new JSONObject(googleResponseJson);
            JSONArray arr=(JSONArray) obj.get("routes");
            obj=(JSONObject)arr.get(0);
            arr=(JSONArray)obj.get("legs");
            obj=(JSONObject) arr.get(0);
            obj=(JSONObject)obj.get("duration");
            String time=obj.getString("text");
            this.time=time;
        }
        catch (Exception e){this.time="Parse error";}
    }

    public String getTime(){return time;}
    public String getDistance(){return distance;}
}
