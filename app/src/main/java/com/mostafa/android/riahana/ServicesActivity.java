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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicesActivity extends AppCompatActivity {
    GridView grid;
    ArrayList<String> services_type, id_list;
    ProgressBar progressBar;
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    private String TAG = ServicesActivity.class.getSimpleName();

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
        setStatusBarColored(this);
        setContentView(R.layout.activity_services);
        progressBar = findViewById(R.id.progress2);
        mAdView = findViewById(R.id.adView1);
        services_type = new ArrayList<String>();
        id_list = new ArrayList<String>();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interServices));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        AdRequest adRequest = new AdRequest.Builder().build();


        final Intent i =new Intent(this,ServicesDetalisActivity.class);
        // mAdView.setAdSize(AdSize.BANNER);
        // mAdView.setAdUnitId(getString(R.string.banner_home_footer));
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //    Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
//               Toast.makeText(getApplicationContext(), "Ad failed to load! error code: "+i , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
//                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {

            }
        });
        mAdView.loadAd(adRequest);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String serviceType = jsonArray.getJSONObject(i).getString("services_type");
                        String id = jsonArray.getJSONObject(i).getString("id_services");
                        services_type.add(serviceType);
                        id_list.add(id);
                    }
                    CustomGridForServices adapter = new CustomGridForServices(ServicesActivity.this, services_type);
                    grid = findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
//                            Toast.makeText(ServicesActivity.this, "You Clicked at " + id_list.get(+position), Toast.LENGTH_SHORT).show();
                            mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {
                                    showInterstitial();
                                }
                            });
                            i.putExtra("id",id_list.get(position));
                            i.putExtra("serviceName",services_type.get(position));
                            startActivity(i);
//                            finish();

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServiceRequest serviceRequest = new ServiceRequest(images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(ServicesActivity.this);
        queue.add(serviceRequest);



    }

    public void backicon(View view) {
        onBackPressed();
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public class ServiceRequest extends StringRequest {
        private final static String ServicesRequestUrl = "http://raihana-eg.com/site_api/api/service_type";
        private Map<String, String> params;

        public ServiceRequest(String lang, Response.Listener<String> listener) {
            super(Method.POST, ServicesRequestUrl, listener, null);
            params = new HashMap<>();
            params.put("lang", lang);
        }

        @Override
        protected Map<String, String> getParams() {
            return params;
        }
    }

}
