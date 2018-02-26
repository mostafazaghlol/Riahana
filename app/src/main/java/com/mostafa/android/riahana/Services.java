package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Services extends AppCompatActivity {
    static String id_services;
    TextView textViewTitle, information1, information2;
    ImageView circleImage;
    Button btRate, btBooking;
    String name, details;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_eyeleft);
            setStatusBarColored(this);
            relativeLayout = findViewById(R.id.idx);
            progressBar = findViewById(R.id.progressser);
            textViewTitle = findViewById(R.id.servicesTitle);
            circleImage = findViewById(R.id.servicesImage);
            information1 = findViewById(R.id.textInformation);
            btRate = findViewById(R.id.RateBt);
            btBooking = findViewById(R.id.BookingBt);
            final Intent i = getIntent();
//            final String name = i.getStringExtra("name");
            id_services=i.getStringExtra("id");
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonResponse = null;
                    try {
                        progressBar.setVisibility(View.INVISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject respons = jsonArray.getJSONObject(i);
                            name = respons.getString("service_name");
                            details = respons.getString("details");
                            textViewTitle.setText(name);
                            information1.setText(Html.fromHtml(details));
                            Picasso.with(getApplicationContext()).load(respons.getString("service_img")).into(circleImage);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            subservicesRequest subservicesRequest1 = new subservicesRequest(images.lang,id_services, listener);
            RequestQueue queue = Volley.newRequestQueue(Services.this);
            queue.add(subservicesRequest1);

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

    public class subservicesRequest extends StringRequest{
        private static final String url="http://raihana-eg.com/site_api/api/service_detail_api";
        private Map<String,String> params;

        subservicesRequest(String lang, String id_subservice, Response.Listener<String> listener){
            super(Method.POST,url,listener,null);
            params = new HashMap<>();
            params.put("lang",lang);
            params.put("id_subservice",id_subservice);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

}
