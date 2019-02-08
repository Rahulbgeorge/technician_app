package com.example.rahul.technicial_side_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.rahul.technicial_side_app.DataTypes.Customer;
import com.example.rahul.technicial_side_app.DataTypes.Technician_service_center_firebase_support;
import com.example.rahul.technicial_side_app.DataTypes.User;
import com.example.rahul.technicial_side_app.alerts.error_dialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

//ACTIVATE RECORDING OPTION BY JUST UNCOMMENTING SETUP RECORDING
//7026606333
public class start_service extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    int minutes=0,seconds=0;
    TextView timer;
    String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gpp";
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);
        customer=(Customer)getIntent().getExtras().get("customer");

        timer=findViewById(R.id.timer);
        startTimer();
        Log.e("audio record","setup triggered");
//        setupAudioRecording();
        updateFirebaseStatus();
    }

    @Override
    public void onBackPressed() {

    }

    public void onScanSpareEquipment(View view)
    {
        /*Intent i = new Intent(this,QrCodeActivity.class);
        startActivityForResult( i,REQUEST_CODE_QR_SCAN);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("permission requested","permission allowed for request code"+requestCode+"and result code"+resultCode);
        if(resultCode != RESULT_OK)
        {
            new error_dialog(this).setMessage("Oops there was a small issue while retreiving your data, Please try agail")
                    .show();
            //Toast.makeText(this,"Some error occured",Toast.LENGTH_SHORT).show();
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Toast.makeText(this,"The result is"+result,Toast.LENGTH_SHORT).show();

        }

        if(requestCode==10)//REQUEST CODE FOR RECORDING PURPOSE
        {
            Log.e("audio record","permissionns allowed");
            String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gpp";
            Log.e("Recording ",outputFile);
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);
            startRecording();
        }
    }

    public void startTimer()
    {
         final Handler handler = new Handler();
         final Runnable runnable = new Runnable(){
            public void run() {
                seconds++;
                if(seconds==60)
                {seconds=0;minutes++;}
                timer.setText(minutes+":"+String.format("%02d", seconds));
                handler.postDelayed(this,1000);

            }
        };

        handler.postDelayed(runnable, 1000);
    }

    MediaRecorder myAudioRecorder;
    public void setupAudioRecording()
    {
        Log.e("audio record","reached the audio recording function");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED
                 || ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            Log.e("audio record","about to request permission");
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO ,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    10);
        } else {
            Log.e("audio record","audio recordinig triggered");
            Log.e("Recording ",outputFile);
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);
            startRecording();
        }


    }

    public void startRecording()
    {
        Log.e("recording","starting recording");
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        }
        catch (Exception e)
        {
            Log.e("audio recorder",e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopRecording()
    {
        Log.e("recording","recording stopped");
        try{
            myAudioRecorder.stop();
            myAudioRecorder.release();
            uploadFile(outputFile,customer.getId());
            Intent i=new Intent(this,Summary_page.class);
            i.putExtra("customer",customer);
            startActivity(i);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopService(View view)
    {
        stopRecording();


    }

    String url="http://192.168.43.2/uploadAudioService/";
    private  void uploadFile(String file_url,String bookingId)
    {
        File f=new File(file_url);
        try {
            MultipartUploadRequest obb=new MultipartUploadRequest(getApplication(),"123",url);

            obb.addParameter("booking_id",bookingId);
            obb.addFileToUpload(f.getPath(),"myfile");
            String a= obb.startUpload();

            Toast.makeText(getApplicationContext(),"File successfully uploaded to "+a,Toast.LENGTH_LONG).show();

           // new DownloadData().execute(new String[]{url});

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    class DownloadData extends AsyncTask<String , Void ,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            DownloadUrl download=new DownloadUrl();
            try {
                Log.e("datadownload","started download process");
                String text=download.readUrl(strings[0]);
                return text;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Some exception occured";

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("datadownloader","downloading complete");
            Log.e("datadownloader","downloaded data is "+s);
            downloadComplete(s);

        }
    }
    void downloadComplete(String text)
    {
        //JsonDecoder decoder=new JsonDecoder();
        //imageDescription=decoder.decode(text);
        //output.setText(text);
        //say(imageDescription);
        Log.e("download return",text+" is the output returned");
    }


    public void updateFirebaseStatus()
    {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("technician");
        Technician_service_center_firebase_support tech_service=new Technician_service_center_firebase_support();
        tech_service.setWorkInProgressStatus(customer.getId());
        Log.e("Customer","id is"+customer.getId());
        User user=new User().loadUserFromMemory(this);
        myRef.child(user.getId()).setValue(tech_service);
    }


}
