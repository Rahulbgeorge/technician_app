package com.example.rahul.technicial_side_app.alerts;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rahul.technicial_side_app.R;


public class error_dialog {
    Activity activity;
    Dialog dialog;
    TextView message;
    Button ok;
    Boolean isResponseButtonSet=false;
    public error_dialog(Activity activity)
    {this.activity=activity;
        dialog=new Dialog(activity);
        dialog.setContentView(R.layout.error_alert);
        message=dialog.findViewById(R.id.message);
        ok=dialog.findViewById(R.id.okbutton);

    }

    public void show()
    {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        if(!isResponseButtonSet)
        {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        dialog.show();
    }

    public error_dialog setMessage(String Message)
    {
        message.setText(Message);
        return this;
    }

    public error_dialog setPositiveButtonText(String buttonText)
    {
        ok.setText(buttonText);
        return this;
    }

    public error_dialog setPositiveButtonResponse(final error_dialog_positive_response response)
    {
        isResponseButtonSet=true;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                response.onPositiveButtonResponse();
            }
        });
        return this;
    }
}

