package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class profileActivity extends AppCompatActivity {
    TextView saveTextview;
    EditText etName,etPhone,etEmail,etpassword;
    AdView mAdView;
    InterstitialAd mInterstitialAd;

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(context);

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
//            view.setBackground(context.getResources().getDrawable(R.drawable.buttonblue));
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
        setContentView(R.layout.activity_profile);
        setStatusBarColored(this);
        etName = findViewById(R.id.name);
        etPhone = findViewById(R.id.phone);
        etEmail = findViewById(R.id.emailaddress);
        etpassword = findViewById(R.id.passwordpro);
        mAdView = findViewById(R.id.adViewprofile);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interProfile));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent i = getIntent();
        final String name=i.getStringExtra("name");
        final String email = i.getStringExtra("email");
        final String phone = i.getStringExtra("phone");
        final String client_id = i.getStringExtra("client_id");
        etName.setText(name,TextView.BufferType.EDITABLE);
        etEmail.setText(email,TextView.BufferType.EDITABLE);
        etPhone.setText(phone,TextView.BufferType.EDITABLE);
        saveTextview = findViewById(R.id.btsave);
        saveTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lan=Locale.getDefault().toString();
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.optJSONArray("message");
                            JSONObject object = jsonArray.optJSONObject(0);
                            int messageID = object.getInt("messageID");
                            String message = object.getString("message");
                            if(messageID == 0){
                                Toast.makeText(profileActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(profileActivity.this, ""+lan, Toast.LENGTH_SHORT).show();
                            }else if(messageID == 1){
                                Toast.makeText(profileActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(profileActivity.this, ""+lan, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                };
                profile_Request profile_Request1 = new profile_Request(client_id, name, phone, email,etpassword.getText().toString(),images.lang,listener);
                RequestQueue queue = Volley.newRequestQueue(profileActivity.this);
                queue.add(profile_Request1);
            }
        });
    }

    public void backicon(View view) {
        onBackPressed();
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public class profile_Request extends StringRequest {
        private static final String LOGIN_REQUEST_URL = "http://raihana-eg.com/site_api/api/edit_profile";
        private Map<String, String> params;

        public profile_Request(String id_client,String name,String phone,String email,String password,String lang,Response.Listener<String> listener) {
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("id_client",id_client);
            params.put("full_name",name);
            params.put("phone",phone);
            params.put("email",email);
            params.put("password",password);
            params.put("lang",lang);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

}
