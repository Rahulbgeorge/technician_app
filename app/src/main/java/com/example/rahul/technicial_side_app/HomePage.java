package com.example.rahul.technicial_side_app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.Customer;
import com.example.rahul.technicial_side_app.DataTypes.DutyList;
import com.example.rahul.technicial_side_app.DataTypes.LoadingDialog;
import com.example.rahul.technicial_side_app.DataTypes.Technician_service_center_firebase_support;
import com.example.rahul.technicial_side_app.DataTypes.User;
import com.example.rahul.technicial_side_app.Database.AsyncFetchUserLocationData;
import com.example.rahul.technicial_side_app.Database.AsyncTodaysDutyResponse;
import com.example.rahul.technicial_side_app.Database.DownloadDutyInformation;
import com.example.rahul.technicial_side_app.Database.fetchUserLocationData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        dutyPage.OnFragmentInteractionListener,
        equipmentPage.OnFragmentInteractionListener
        ,AsyncTodaysDutyResponse,
        LocationListener,
        AsyncFetchUserLocationData{

    TabLayout tablayout;
    ViewPager pager;
    User user;
    ArrayList<Customer> customers;
    LatLng myPos=null;
//    boolean isDistanceValueFetched=false;
//    boolean isDutyListFetched=false;
    LoadingDialog loader;

    String AndroidApiKey="AIzaSyCwkoMXTW-JgRBBHOWG4jyt9e75r5Sx5BQ";
    boolean isDistanceDataFromGooleDownloaded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        loader=new LoadingDialog(this);
        loader.setMessage("Fetching Tickets").show();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        widgetInitializer();
        if(user==null) {
            SharedPreferences pref=getSharedPreferences("technician_side_app",MODE_PRIVATE);
            String userjson=pref.getString("jsonUser","null");
            Log.e("fetched data",userjson);
            if(userjson!=null)
            {
                user=new Gson().fromJson(userjson,User.class);
            }
            else
            {
                Log.e("error occured","No jsono file found");
            }
        }
        //FETCH DATA FROM API
        new DownloadDutyInformation(this,user.getId());

        fetchLocationData();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void widgetInitializer()
    {
        customers=new ArrayList<Customer>();
        tablayout=findViewById(R.id.tabLayout);
         pager=(ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        tabInitialilzer(customers);
        DecoView arcView = (DecoView)findViewById(R.id.dynamicArcView);
        final TextView perc=findViewById(R.id.perc);
// Create background track
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(25f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(25f)
                .build();

        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(100)
                .setDuration(1000)
                .build());

        int series1Index = arcView.addSeries(seriesItem1);
        arcView.addEvent(new DecoEvent.Builder(30).setIndex(series1Index).setDelay(100).build());




    }


    HomePageAdapter adapter;
    private void tabInitialilzer(ArrayList<Customer> customers)
    {
        tablayout.addTab(tablayout.newTab().setText("Duty"));
        tablayout.addTab(tablayout.newTab().setText("Equipment"));
        //final order_pager_adapter adapter=new order_pager_adapter(getSupportFragmentManager(),tabLayout.getTabCount());
         adapter=new HomePageAdapter(getSupportFragmentManager(),customers);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    DutyList dutylist;
    @Override
    public void todaysDutyDownloadedSuccessfully(ArrayList<Customer> duty_customer) {
//        isDutyListFetched=true;
        dutylist=new DutyList(duty_customer,this);
        Log.e("duty download","duty downloaded successfully");
        //to be loaded once the location data is downloaded
        //dutylist.loadDutyToLocalMemory();
        initializeFirebase();
        this.customers=duty_customer;
        if(myPos!=null)
        {
            downloadUserDistanceData(customers);
        }
        if(adapter!=null) {
            adapter = new HomePageAdapter(getSupportFragmentManager(), customers);
            pager.setAdapter(adapter);
            //((HomePage)this.getBaseContext()).tabInitialilzer(duty_customer);
        }
        }

    @Override
    public void todaysDutyDownloadFailed(String description) {
        Log.e("duty download","fail during download"+description);
    }

    public void initializeFirebase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("technician");
        Technician_service_center_firebase_support tech_Service=new Technician_service_center_firebase_support();
        myRef.child(user.getId()).setValue(tech_Service);
    }

    final int MY_PERMISSIONS_REQUEST_LOCATION=1212;
    LocationManager locationManager;
    public void fetchLocationData()
    {
         locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!statusOfGPS) {
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            return;
        }
        else {
            Toast.makeText(this, "Location is triggered from here acc", Toast.LENGTH_LONG).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Toast.makeText(this, "Loccation is triggered already", Toast.LENGTH_LONG).show();
        }
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
               /* Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsOptionsIntent);*/
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Toast.makeText(this,"Toast call is triggered from abc",Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                Toast.makeText(this,"Location change started",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        myPos=new LatLng(location.getLatitude(),location.getLongitude());
        Log.e("Location fetched","location fetched");
        if(customers!=null && !isDistanceDataFromGooleDownloaded)
        {
            downloadUserDistanceData(customers);
        }
        /*try {
            JSONObject obj;
            JSONArray arr=new JSONArray();
            if (isDutyListFetched)
                if (!isDistanceValueFetched) {
                    obj=new JSONObject();

                    for (Customer cust : customers) {
                        obj=new JSONObject();
                        obj.put("bookingId",cust.getId());
                        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
                        googleDirectionsUrl.append("origin=" + myPos.latitude + "," + myPos.longitude);//pass source location over here
                        googleDirectionsUrl.append("&destination=" + cust.getLat() + "," + cust.getLng());//pass destination location over here
                        googleDirectionsUrl.append("&key=" + AndroidApiKey);
                        obj.put("url",googleDirectionsUrl.toString());
                        arr.put(obj);
                        Log.e("dataa",googleDirectionsUrl.toString());


                    }


                    new fetchUserLocationData(this,arr);
                }
        }
        catch (Exception e)
        {}*/



    }

    public void downloadUserDistanceData(ArrayList<Customer> customers)
    {
        isDistanceDataFromGooleDownloaded=true;
        Log.e("donwload","user distance data download triggered");
        JSONObject obj;
        JSONArray arr=new JSONArray();
        obj=new JSONObject();
try {
    for (Customer cust : customers) {
        obj = new JSONObject();
        obj.put("bookingId", cust.getId());

        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + myPos.latitude + "," + myPos.longitude);//pass source location over here
        googleDirectionsUrl.append("&destination=" + cust.getLat() + "," + cust.getLng());//pass destination location over here
        googleDirectionsUrl.append("&key=" + AndroidApiKey);
        obj.put("url", googleDirectionsUrl.toString());

        arr.put(obj);
        Log.e("dataa", googleDirectionsUrl.toString());


    }

    new fetchUserLocationData(this, arr);
}
catch (Exception e)
{
    e.printStackTrace();
    Log.e("crash occured","crash during json parsing");
}
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }



    //USER LOCATION FETCHED
    @Override
    public void userlocationDataFetchComplete(JSONObject object) {

        Log.e("user location","data is fetched");
        try {

            for (Customer cust : customers) {
                JSONArray arr=(JSONArray) object.get(cust.getId());
                cust.setDuration(arr.getString(0));
                cust.setDistance(arr.getString(1));
            }
            loader.dismiss();
            adapter = new HomePageAdapter(getSupportFragmentManager(), customers);
            pager.setAdapter(adapter);
        }
        catch (Exception e)
        {
            loader.dismiss();
            Toast.makeText(this,"User location fetced but some error occured",Toast.LENGTH_LONG).show();
            loader.setMessage("Error");
        }
    }

    @Override
    public void userLocationDataFetchFail() {
        Log.e("user location","data is failed");

    }
}
