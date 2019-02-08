package com.example.rahul.technicial_side_app.alerts;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.rahul.technicial_side_app.R;
public class success_dialog {
    Activity activity;
    Dialog dialog;
    TextView message;
    Button ok;
    public success_dialog(Activity activity)
    {this.activity=activity;
        dialog=new Dialog(activity);
        dialog.setContentView(R.layout.success_alert);
        message=dialog.findViewById(R.id.message);
        ok=dialog.findViewById(R.id.okbutton);

    }

    public void show()
    {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public success_dialog setMessage(String Message)
    {
        message.setText(Message);
        return this;
    }

    public success_dialog setPositiveButtonText(String buttonText)
    {
        ok.setText(buttonText);
        return this;
    }

    public success_dialog setPositiveButtonResponse(final success_dialog_positive_response response)
    {
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

