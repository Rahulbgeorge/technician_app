package com.example.rahul.technicial_side_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rahul.technicial_side_app.DataTypes.Customer;
import com.example.rahul.technicial_side_app.DataTypes.Technician_service_center_firebase_support;
import com.example.rahul.technicial_side_app.DataTypes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Summary_page extends AppCompatActivity {

    User user;
    TextView out;
    ImageView img;
    ProgressBar pb;
    MediaPlayer mp;
    String pay="0";
    Customer cust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);
        user=new User().loadUserFromMemory(this);
        cust=(Customer) getIntent().getExtras().get("customer");
        out=findViewById(R.id.text_out);
        img=findViewById(R.id.outimage);
        pb=findViewById(R.id.progressBar);
        updateFirebaseStatus();
        setFirebaseListener();
          mp = MediaPlayer.create(this, R.raw.message);

    }


    public void setFirebaseListener()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("technician");


        // Read from the database
        myRef.child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Technician_service_center_firebase_support tech_service=dataSnapshot.getValue(Technician_service_center_firebase_support.class);
                Log.e("Service status",tech_service.status+"is the status");
                Log.e("Service pay",tech_service.pay+" is the pay to be made");
                if(tech_service.status==4) {
                    img.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    out.setText("Collect ₹" + tech_service.pay + " from the customer");
                    mp.start();
                }
                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }



    public void serviceCompleted(View view)
    {

    }

    public void updateFirebaseStatus()
    {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("technician");
        Technician_service_center_firebase_support tech_service=new Technician_service_center_firebase_support();
        tech_service.setWorkCompletedStatus("Work completed now",cust.getId());
        User user=new User().loadUserFromMemory(this);
        myRef.child(user.getId()).setValue(tech_service);
    }

    public void complete(View view)
    {
        startActivity(new Intent(this,HomePage.class));
    }

    public void callSupport(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:7026606333"));
        startActivity(intent);
    }

}
