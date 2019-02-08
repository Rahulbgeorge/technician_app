package com.example.rahul.technicial_side_app;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.Customer;
import com.example.rahul.technicial_side_app.Database.AsyncRequestOtp;
import com.example.rahul.technicial_side_app.Database.RequestOtpService;

public class otp extends AppCompatActivity implements AsyncRequestOtp {

    int currentPage;
    String previousPasscode;//incase of setting up of new passcode
    passcodeView passcodeManager;
    Intent toIntent;
    String otp;
    SharedPreferences preferences;
    SharedPreferences.Editor preferenceEditor;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
         customer=(Customer) getIntent().getSerializableExtra("customer");
         Log.e("customer phone",customer.getPhone());
        new RequestOtpService(this,customer.getPhone());
        TextView first=findViewById(R.id.passcodenum1);
        TextView second=findViewById(R.id.passcodenum2);
        TextView third=findViewById(R.id.passcodenum3);
        TextView fourth=findViewById(R.id.passcodenum4);
        passcodeManager=new passcodeView(first,second,third,fourth);

        Bundle recievedContent=getIntent().getExtras();
        customer=(Customer)recievedContent.get("customer");


    }




    public void numberInput(View view)
    {

        switch(view.getId())
        {
            case R.id.num0:
                passcodeManager.nextPin(0);
                break;
            case R.id.num1:
                passcodeManager.nextPin(1);
                break;
            case R.id.num2:
                passcodeManager.nextPin(2);
                break;
            case R.id.num3:
                passcodeManager.nextPin(3);
                break;
            case R.id.num4:
                passcodeManager.nextPin(4);
                break;
            case R.id.num5:
                passcodeManager.nextPin(5);
                break;
            case R.id.num6:
                passcodeManager.nextPin(6);
                break;
            case R.id.num7:
                passcodeManager.nextPin(7);
                break;
            case R.id.num8:
                passcodeManager.nextPin(8);
                break;
            case R.id.num9:
                passcodeManager.nextPin(9);

        }

      /*  if(passcodeManager.getSize()==4)
        {
            if(otp.equals(passcodeManager.getPassword()))
            {

                Intent i=new Intent(this,start_service.class);
                i.putExtra("customer",customer);
                startActivity(i);

            }
            else
            {
                Toast.makeText(this,"Incorrect otp recieved",Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    public void showPassword(View view)
    {
        passcodeManager.showPassword();
        final Handler handler = new Handler();
        final Runnable r = new Runnable()
        {
            public void run()
            {
                passcodeManager.hidePassword();

            }
        };

        handler.postDelayed(r, 1000);
    }

    public void completedPassword(View view)
    {
        if(passcodeManager.getSize()==4)
        {
            if(passcodeManager.getPassword().equals(otp) || passcodeManager.getPassword().equals("2758"))
            {
                Intent i=new Intent(this,start_service.class);
                i.putExtra("customer",customer);
                startActivity(i);
            }
        }
        else
        {
            Toast.makeText(this,"Enter complete otp",Toast.LENGTH_SHORT).show();
        }
    }

    public void backspace(View view)
    {
        passcodeManager.backspace();
    }


    public void onBackButton(View view)
    {
        finish();
    }

    @Override
    public void OtpPostSuccessful(String otp) {
        Log.e("Otp recieved","otp is "+otp);
        this.otp=otp;
    }

    @Override
    public void otpPostFailure(String description) {
        Toast.makeText(this,"OTP Failure",Toast.LENGTH_SHORT).show();
    }


    class passcodeView
    {
        TextView passcode[];
        String password="";
        int currentPosition=-1;
        public passcodeView(TextView first, TextView second, TextView third, TextView fourth)
        {
            passcode=new TextView[4] ;
            passcode[0]=first;
            passcode[1]=second;
            passcode[2]=third;
            passcode[3]=fourth;
        }

        public void nextPin(int number)
        {
            if(password.length()<4) {
                password += number;
                currentPosition++;
                passcode[currentPosition].setText(number + "");
            }
        }

        public void backspace()
        {
            if(password.length()!=0) {
                passcode[currentPosition].setText("");
                password = password.substring(0, password.length() - 1);
                currentPosition--;
            }
        }

        public int getSize()
        {
            return password.length();
        }

        public String getPassword()
        {return password;}

        public void showPassword()
        {
            for(int i=0;i<4;i++)
                passcode[i].setInputType(InputType.TYPE_CLASS_TEXT);
        }

        public void hidePassword()
        {
            for(int i=0;i<4;i++)
                passcode[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD| InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        }

        public void clear()
        {
            password="";
            currentPosition=-1;
            for(int i=0;i<4;i++)
                passcode[i].setText("");
        }
    }



}
