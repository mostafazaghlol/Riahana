package com.mostafa.android.riahana;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    TextView titelTextview, dateTextView,TimeTextView;
    ImageView ServiceType,Time;
    int millisecond,second,minute,hour,hourofday,dayofyear,year,dayofweek,dayofmonth,month;
    android.support.v4.app.DialogFragment TimePicker;
    android.support.v4.app.DialogFragment datapicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         SharedPreferences sharedPreferences = getSharedPreferences("pref",0);
         Login ob = new Login();
        if(sharedPreferences.contains("user") && sharedPreferences.getInt("login",0)==1) {
            Toast.makeText(this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(BookingActivity.this,Login.class);
            BookingActivity.this.startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_booking);
        Intent i = getIntent();
        Calendar cal = Calendar.getInstance();

        millisecond = cal.get(Calendar.MILLISECOND);
        second = cal.get(Calendar.SECOND);
        minute = cal.get(Calendar.MINUTE);
        //12 hour format
        hour = cal.get(Calendar.HOUR);
        //24 hour format
        hourofday = cal.get(Calendar.HOUR_OF_DAY);
        dayofyear = cal.get(Calendar.DAY_OF_YEAR);
        year = cal.get(Calendar.YEAR);
        dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);

        titelTextview = (TextView)findViewById(R.id.serviceType);
        dateTextView = (TextView)findViewById(R.id.serviceType2);
        ServiceType = (ImageView)findViewById(R.id.serviceTypeImage);
        TimeTextView  = (TextView)findViewById(R.id.serviceType22);
        String currentdatreString2 = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        dateTextView.setText(currentdatreString2);
        TimeTextView.setText("At " + hourofday + ":" + minute);

        //hourOfDay =hourOfDay-12;
        Time = (ImageView)findViewById(R.id.Time);
        if(i.getStringExtra("Title")!= ""){
            titelTextview.setText(i.getStringExtra("Title"));
        }else {
            titelTextview.setText(getResources().getString(R.string.EyelidTightening));

        }
        final Intent intent = new Intent(this,servicesTypeActivity.class);
        intent.putExtra("cat",0);
        ServiceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                finish();
                startActivity(intent);
                images.y = 1;
            }catch (Exception e){
                Log.e("onclick ", "onClick: error"+e.getMessage());
            }
            }
        });
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(images.y==0){
                    Toast.makeText(BookingActivity.this, getResources().getString(R.string.chooseyourserv), Toast.LENGTH_SHORT).show();
                }else {
                   getTime();
                }

            }
        });

    }
    int choosedYear,choosedMonth,choosedDay;

    @Override
    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
            Calendar c  = Calendar.getInstance();
            c.set(Calendar.YEAR,mYear);
            c.set(Calendar.MONTH,mMonth);
            c.set(Calendar.DAY_OF_MONTH,mDay);
            String currentdatreString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
            choosedYear = mYear;
            choosedDay = mDay;
            choosedMonth = mMonth;
            dateTextView.setText(currentdatreString);

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int mHourOfDay, int mMinute) {

        String AM_PM ;
        int mHour=mHourOfDay;
        if(mHourOfDay < 12) {
            AM_PM = "AM";
        } else {
            mHourOfDay = mHourOfDay-12;
            AM_PM = "PM";
        }
        if(choosedYear>=year && choosedMonth>=month&&choosedDay>=dayofmonth && mHour >= hourofday) {

            TimeTextView.setText("At " + mHourOfDay + ":" + minute + " " + AM_PM);
        }else {
            Toast.makeText(this, ""+getResources().getString(R.string.InvalidDate), Toast.LENGTH_SHORT).show();
            getTime();
        }


    }

    public void backicon(View view) {
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
//            view.setBackground(context.getResources().getDrawable());
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

    public void finish(View view) {
        Toast.makeText(this, " "+dayofmonth, Toast.LENGTH_SHORT).show();
    }

    public void getTime(){
        TimePicker = new TimePickerFragment();
        TimePicker.show(getSupportFragmentManager(), "time picker");
        datapicker = new datePickerFragment();
        datapicker.show(getSupportFragmentManager(), "date picker");
    }

}
