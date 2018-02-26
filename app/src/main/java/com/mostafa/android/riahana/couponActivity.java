package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class couponActivity extends AppCompatActivity {
    String coupondata;
    TextView txCoupondata;
    TextView imCoupon;
    RecyclerView rv;
    private List<coupon> coupons;
    private Context context;
    ProgressBar progressBar;
    TextView txNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_coupon);
        txCoupondata = (TextView)findViewById(R.id.coupondata);
        imCoupon = (TextView) findViewById(R.id.facecoupon);
        context = this;
        coupons = new ArrayList<>();
        txNo = (TextView)findViewById(R.id.txNo3);
        progressBar = (ProgressBar)findViewById(R.id.progress20);
        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        final String id_client = sharedPreferences.getString("client_id"," ");
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            coupons.add(new coupon(jsonObject1.getString("code_coupon"),jsonObject1.getString("comment"),jsonObject1.getString("id_coupon")));
                        }
                    }else{
                        txNo.setVisibility(View.VISIBLE);
                        txNo.setText(getString(R.string.nocoupun));
                    }
                    rv = (RecyclerView)findViewById(R.id.rvCoupon);
                    LinearLayoutManager llm = new LinearLayoutManager(couponActivity.this);
                    rv.setLayoutManager(llm);
                    RVAdaptercopuon adapter = new RVAdaptercopuon(coupons,context);
                    rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        couponRequest couponRequest= new couponRequest(images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(couponActivity.this);
        queue.add(couponRequest);


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

    public void backIcon(View view) {
        onBackPressed();
    }

    public class couponRequest extends StringRequest{
        private final static String url="http://raihana-eg.com/site_api/api/coupon";
        private Map<String,String> params;

        public couponRequest(String lang, Response.Listener<String> listener)
        {
            super(Method.POST,url,listener,null);
            params = new HashMap<>();
            params.put("lang",lang);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }


}
