package com.example.rahul.technicial_side_app.DataTypes;

public class Technician_service_center_firebase_support {

    public  int status=0;
    public String work_report="";
    public  int pay=-1;
    public String currentBookingId="";

    public Technician_service_center_firebase_support()
    {

    }



    public void setInProgressStatus(String currentBookingId)
    {
        this.status=1;
        this.currentBookingId=currentBookingId;
    }
    public void setWorkCompletedStatus(String status,String currentBookingId)
    {
        this.currentBookingId=currentBookingId;
        this.status=3;
        this.work_report=work_report;
    }

    public void setWorkInProgressStatus(String currentBookingId)
    {
        this.currentBookingId=currentBookingId;
        this.status=2;

    }



}
