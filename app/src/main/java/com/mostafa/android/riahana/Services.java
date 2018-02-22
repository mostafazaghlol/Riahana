package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Services extends AppCompatActivity {
    TextView textViewTitle, information1, information2;
    ImageView circleImage;
    Button btRate, btBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_eyeleft);
            setStatusBarColored(this);
            textViewTitle = (TextView) findViewById(R.id.servicesTitle);
            circleImage = (ImageView) findViewById(R.id.servicesImage);
            information1 = (TextView) findViewById(R.id.textInformation);
            btRate = (Button) findViewById(R.id.RateBt);
            btBooking = (Button) findViewById(R.id.BookingBt);
            final Intent i = getIntent();
            final String name = i.getStringExtra("name");
            textViewTitle.setText(name);
            final String id_services=i.getStringExtra("id");
            information1.setText(i.getStringExtra("description"));
            Picasso.with(this).load(i.getStringExtra("imageurl")).into(circleImage);
            btRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialogClass cdd = new CustomDialogClass(Services.this);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                }
            });
            final Intent bookingIntent = new Intent(this, BookingActivity.class);
            btBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookingIntent.putExtra("Title", name);
                    bookingIntent.putExtra("id",id_services);
                    images.y = 1;
                    startActivity(bookingIntent);
                    finish();

                }
            });
        } catch (Exception e) {
            Log.e("Services", " " + e.getMessage());
        }
    }

    public void backIcon(View view) {
        onBackPressed();
    }

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
