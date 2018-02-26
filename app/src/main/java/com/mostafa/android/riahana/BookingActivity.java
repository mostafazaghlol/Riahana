package com.mostafa.android.riahana;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView titelTextview, dateTextView, TimeTextView;
    RelativeLayout ServiceType, Time;
    int millisecond, second, minute, hour, hourofday, dayofyear, year, dayofweek, dayofmonth, month;
    android.support.v4.app.DialogFragment TimePicker;
    android.support.v4.app.DialogFragment datapicker;
    int choosedYear, choosedMonth, choosedDay;
    Button finish;
    String currentdatreString;

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        Login ob = new Login();
        if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
            if (images.lang == "1") {
                Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent i = new Intent(BookingActivity.this, Login.class);
            images.activity=0;
            images.login = 2;
            BookingActivity.this.startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_booking);
        final Intent i = getIntent();
        finish = findViewById(R.id.finish);
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

        titelTextview = findViewById(R.id.serviceType);
        dateTextView = findViewById(R.id.serviceType2);
        ServiceType = findViewById(R.id.serviceTypeImage);
        String currentdatreString2 = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        currentdatreString = currentdatreString2;
        dateTextView.setText(currentdatreString2);
        final String id_client = sharedPreferences.getString("client_id", " ");
        final String x = i.getStringExtra("id");
        //hourOfDay =hourOfDay-12;
        Time = findViewById(R.id.Time);
        if (i.getStringExtra("Title") != "") {
            titelTextview.setText(i.getStringExtra("Title"));
        } else {
            titelTextview.setText(getResources().getString(R.string.EyelidTightening));

        }
        final Intent intent = new Intent(this, servicesTypeActivity.class);
        intent.putExtra("cat", 0);
        ServiceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finish();
                    startActivity(intent);
                    images.y = 1;
                } catch (Exception e) {
                    Log.e("onclick ", "onClick: error" + e.getMessage());
                }
            }
        });
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (images.y == 0) {
                    Toast.makeText(BookingActivity.this, getResources().getString(R.string.chooseyourserv), Toast.LENGTH_SHORT).show();
                } else {
                    getTime();
                }

            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray messagearray = jsonObject.optJSONArray("message");
                            for (int i = 0; i < messagearray.length(); i++) {
                                String message = messagearray.getJSONObject(i).getString("message");
                                Toast.makeText(BookingActivity.this, " " + message, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                BookRequest bookRequest = new BookRequest(id_client, x, currentdatreString, images.lang, listener);
                RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
                queue.add(bookRequest);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, mYear);
        c.set(Calendar.MONTH, mMonth);
        c.set(Calendar.DAY_OF_MONTH, mDay);
        currentdatreString = mYear+"-"+(mMonth+1)+"-"+mDay;
        String currentdatreString2 = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        choosedYear = mYear;
        choosedDay = mDay;
        choosedMonth = mMonth;
        if (choosedYear >= year && choosedMonth >= month && choosedDay >= dayofmonth) {
            dateTextView.setText(currentdatreString2);
        } else {
            Toast.makeText(this, "" + getResources().getString(R.string.InvalidDate), Toast.LENGTH_SHORT).show();
            getTime();
        }



    }

    @Override
    public void onTimeSet(TimePicker timePicker, int mHourOfDay, int mMinute) {

        String AM_PM;
        int mHour = mHourOfDay;
        if (mHourOfDay < 12) {
            AM_PM = "AM";
        } else {
            mHourOfDay = mHourOfDay - 12;
            AM_PM = "PM";
        }
//        currentdatreString = currentdatreString + "/n" +time;
        if (choosedYear >= year && choosedMonth >= month && choosedDay >= dayofmonth && mHour >= hourofday) {
            String time ="At " + mHourOfDay + ":" + minute + " " + AM_PM;
            TimeTextView.setText(time);

        } else {
            Toast.makeText(this, "" + getResources().getString(R.string.InvalidDate), Toast.LENGTH_SHORT).show();
            getTime();
        }


    }

    public void backicon(View view) {
        onBackPressed();
    }

    public void getTime() {
        datapicker = new datePickerFragment();
        datapicker.show(getSupportFragmentManager(), "date picker");
    }


    public class BookRequest extends StringRequest {
        private final static String url = "http://raihana-eg.com/site_api/api/booking_api";
        private Map<String, String> params;


        public BookRequest(String id_client, String id_serivce, String datetime, String lang, Response.Listener<String> listener) {
            super(Method.POST, url, listener, null);
            params = new HashMap<>();
            params.put("id_client", id_client);
            params.put("id_serivce", id_serivce);
            params.put("datetime", datetime);
            params.put("lang", lang);
        }

        @Override
        protected Map<String, String> getParams() {
            return params;
        }
    }

}
