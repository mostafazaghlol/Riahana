package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class Services extends AppCompatActivity {
    TextView textViewTitle,information1,information2;
    ImageView circleImage;
    Button btRate,btBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyeleft);
        setStatusBarColored(this);
        textViewTitle = (TextView)findViewById(R.id.servicesTitle);
        circleImage = (ImageView)findViewById(R.id.servicesImage);
        information1 = (TextView)findViewById(R.id.textInformation);
        information2 = (TextView)findViewById(R.id.textInformation2);
        btRate = (Button)findViewById(R.id.RateBt);
        btBooking = (Button)findViewById(R.id.BookingBt);
        Intent i = getIntent();
        textViewTitle.setText(i.getStringExtra("Title"));
        circleImage.setImageResource(i.getIntExtra("Image",R.drawable.placeholder));
        information1.setText(i.getStringExtra("information1"));
        information2.setText(i.getStringExtra("information2"));
        btRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Services.this).create(); //Read Update
                alertDialog.setTitle("Rate Services");
                alertDialog.setMessage("this is my app");



                alertDialog.show();
            }
        });
    }

    public void backIcon(View view) {
        onBackPressed();
    }

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(context);

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackground(context.getResources().getDrawable(R.drawable.buttonblue));
        }
    }
    public static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
