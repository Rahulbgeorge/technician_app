package com.example.rahul.technicial_side_app.DataTypes;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.example.rahul.technicial_side_app.R;


public class LoadingDialog {
    Activity activity;
    Dialog loader;
    TextView loadingText;
    CardView cv;
    public LoadingDialog(Activity activity)
    {
        this.activity=activity;
        loader=new Dialog(this.activity);
        loader.setContentView(R.layout.authentication_loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCanceledOnTouchOutside(false);
        cv=loader.findViewById(R.id.cv);
        loadingText=loader.findViewById(R.id.loadingMessage);
    }

    public LoadingDialog setBlueDialog()
    {   cv.setCardBackgroundColor(this.activity.getResources().getColor(R.color.colorPrimary));
        loadingText.setTextColor(this.activity.getResources().getColor(R.color.white));
        return this;}

    public LoadingDialog setMessage(String message)
    {
        this.loadingText.setText(message);
        return this;
    }

    public void show()
    {
        this.loader.show();
    }

    public void dismiss()
    {
        this.loader.dismiss();
    }
}
