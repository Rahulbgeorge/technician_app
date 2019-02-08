package com.example.rahul.technicial_side_app.DataTypes;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Customer implements Serializable {
    String name;
    String street;
    String homeno;
    double lat,lng;
    String phone;
    String problem;
    String deviceName;
    String deviceId;
    String id;
    String duration;
    String distance;

    public Customer(String name, String street, String phone,double latitude, double longitude,String homeno,String deviceName,String deviceId,String problem,String customerId)
    {
        this.name=name;
        this.street=street;
        this.homeno=homeno;
        this.lat=latitude;
        this.lng=longitude;
        this.phone=phone;
        this.problem=problem;
        this.deviceName=deviceName;
        this.deviceId=deviceId;
        this.id=customerId;
        this.duration="";
        this.distance="";

    }

    public String getDuration(){return  this.duration;}
    public void setDuration(String duration){this.duration=duration;}
    public String getName()
    {return this.name;}
    public String getStreet()
    {return this.street;}
    public String getHomeno()
    {return this.homeno;}
    public double getLat()
    {return this.lat;}
    public double getLng()
    {return this.lng;}
    public String getPhone()
    {return this.phone;}
    public String getId()
    {return this.id;}

    public void setDistance(String distance)
    {this.distance=distance;}

    public String getDistance()
    {return this.distance;}


}
