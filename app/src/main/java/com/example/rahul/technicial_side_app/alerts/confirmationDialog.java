package com.example.rahul.technicial_side_app.alerts;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rahul.technicial_side_app.R;


public class confirmationDialog {

    Activity activity;
    Dialog dialog;
    TextView message;
    Button ok, no;
    public confirmationDialog(Activity activity)
    {this.activity=activity;
        dialog=new Dialog(activity);
        dialog.setContentView(R.layout.confirmation_dialog);
        message=dialog.findViewById(R.id.message);
        ok=dialog.findViewById(R.id.okbutton);
        no=dialog.findViewById(R.id.notokbutton);

    }

    public void show()
    {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public confirmationDialog setMessage(String Message)
    {
        message.setText(Message);
        return this;
    }

    public confirmationDialog setPositiveButtonText(String buttonText)
    {
        ok.setText(buttonText);
        return this;
    }

    public confirmationDialog setNegativeButtonText(String buttonText)
    {
        no.setText(buttonText);
        return this;
    }

    public confirmationDialog setResponseListener(final confirmation_dialog_response response)
    {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                response.positiveConfirmationResponse();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                response.negativeConfirmationResponse();
            }
        });

        return this;
    }




}


