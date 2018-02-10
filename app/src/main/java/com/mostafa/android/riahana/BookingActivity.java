package com.mostafa.android.riahana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BookingActivity extends AppCompatActivity {
    TextView titelTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent i = getIntent();
        titelTextview = (TextView)findViewById(R.id.serviceType);
        titelTextview.setText(i.getStringExtra("Title"));

    }
}
