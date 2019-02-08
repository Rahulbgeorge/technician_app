package com.example.rahul.technicial_side_app.Database;

import org.json.JSONArray;
import org.json.JSONObject;

public interface AsyncFetchUserLocationData {
    void userlocationDataFetchComplete(JSONObject obj);
    void userLocationDataFetchFail();
}
