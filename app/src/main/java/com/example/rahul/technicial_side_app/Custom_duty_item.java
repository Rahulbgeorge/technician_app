package com.example.rahul.technicial_side_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.Customer;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Custom_duty_item extends BaseAdapter {

    ArrayList<Customer> customers;
    Activity activity;
    public Custom_duty_item(Activity activity, ArrayList<Customer> customers)
    {
        this.activity=activity;
        this.customers=customers;
        Log.e("Custom duty item","this is from the item adapter"+this.customers.size());
    }

    @Override
    public int getCount() {
        return this.customers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    Customer cust;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=this.activity.getLayoutInflater().inflate(R.layout.duty_list_item,null);
         cust=this.customers.get(i);

            TextView username=view.findViewById(R.id.username);
            TextView street=view.findViewById(R.id.street);
            TextView homeno=view.findViewById(R.id.homeno);
                    username.setText(cust.getName());
                    street.setText(cust.getStreet());
                    homeno.setText(cust.getPhone());
             TextView duration=view.findViewById(R.id.duration);
             duration.setText(cust.getDuration());
             TextView distance=view.findViewById(R.id.distance);
             distance.setText(cust.getDistance());


        return view;
    }

    public void dutySelected(Customer cust)
    { Intent i=new Intent(this.activity,duty_map.class);
      i.putExtra("customer",cust);
      this.activity.startActivity(i);
    }
}
