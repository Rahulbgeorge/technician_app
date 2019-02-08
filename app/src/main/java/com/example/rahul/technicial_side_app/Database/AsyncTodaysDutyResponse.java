package com.example.rahul.technicial_side_app.Database;

import com.example.rahul.technicial_side_app.DataTypes.Customer;

import java.util.ArrayList;

public interface AsyncTodaysDutyResponse {
    void todaysDutyDownloadedSuccessfully(ArrayList<Customer> duty_customer);
    void todaysDutyDownloadFailed(String description);
}
