package com.example.rahul.technicial_side_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.rahul.technicial_side_app.DataTypes.Customer;

import java.util.ArrayList;

public class HomePageAdapter extends FragmentStatePagerAdapter {

    ArrayList<Customer> customers;
    public HomePageAdapter(FragmentManager fm, ArrayList<Customer> customers) {
        super(fm);
        this.customers=customers;
    }
    View layout;
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                dutyPage obb=dutyPage.newInstance(customers,"first");
                //dutyPage obb=new dutyPage(); cannot pass a value to a fragment directly
                return obb;
            case 1:
                equipmentPage ep=new equipmentPage();
                return ep;
        }
        return new dutyPage();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
