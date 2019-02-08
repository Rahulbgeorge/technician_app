package com.example.rahul.technicial_side_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDecoder {

    double EXPECTECTED_CONFIDENCE=0.65;
    public String decode(String input)
    {
        try {
            JSONArray array=new JSONArray(input);
            for(int i=0;i<array.length();i++)
            {
                JSONObject obj= (JSONObject) array.get(i);
                String objectName=(String)obj.get("label");
                return "I can see a "+objectName+"";

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Hmmmm! that looks tricky, Try Again!";
        }
        return null;
    }
}
