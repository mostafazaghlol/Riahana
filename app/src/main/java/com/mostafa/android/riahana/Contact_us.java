package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Contact_us extends AppCompatActivity {
    TextView phone_sales,phone_support,whatsapp,email_support,email_sales,location;
    ImageView facebook;
    ProgressBar progressBar;
    String facebookUrl,iframe;
    WebView webView;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_contact_us);
        phone_sales =(TextView)findViewById(R.id.phone_sales);
        phone_support=(TextView)findViewById(R.id.phone_support);
        email_sales =(TextView)findViewById(R.id.email_sales);
        email_support=(TextView)findViewById(R.id.email_support);
        whatsapp = (TextView)findViewById(R.id.whatsapp);
        location = (TextView)findViewById(R.id.location);
        facebook = (ImageView)findViewById(R.id.facebook);
        progressBar = (ProgressBar)findViewById(R.id.progress5);
        webView = (WebView)findViewById(R.id.web);
        linearLayout = (LinearLayout)findViewById(R.id.linear);

        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    JSONObject jsonObject1 =jsonArray.getJSONObject(0);
                    phone_sales.setText(jsonObject1.getString("phone_sales"));
                    phone_support.setText(jsonObject1.getString("phone_support"));
                    whatsapp.setText(jsonObject1.getString("whatsapp"));
                    email_sales.setText(jsonObject1.getString("email_sales"));
                    email_support.setText(jsonObject1.getString("email_support"));
                    location.setText(jsonObject1.getString("address"));
                    facebookUrl = jsonObject1.getString("facebook");
                    iframe=jsonObject1.getString("map");
                    webView.loadData(iframe,"text/html","utf-8");
                    webView.getSettings().setJavaScriptEnabled(true);
                    facebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(facebookUrl));
                            Contact_us.this.startActivity(i);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        contactus contactus = new contactus(images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(Contact_us.this);
        queue.add(contactus);


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
            view.setBackgroundColor(context.getResources().getColor(R.color.blue));
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

    public class contactus extends StringRequest{
        private final static String url="http://raihana-eg.com/site_api/api/contact_api";
        private Map<String,String> params;
        public contactus(String lang, Response.Listener<String> listener){
            super(Method.POST,url,listener,null);
            params = new HashMap<>();
            params.put("lang",lang);
        }

        @Override
        protected Map<String, String> getParams(){
            return params;
        }
    }
}
