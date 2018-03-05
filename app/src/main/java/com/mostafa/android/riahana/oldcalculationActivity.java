package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class oldcalculationActivity extends AppCompatActivity {
    RecyclerView rv;
    ProgressBar progressBar;
    TextView txNo;
    AdView mAdView;
    private List<calculatedate> calculates;
    private Context context;

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
        setContentView(R.layout.activity_oldcalculation);
        context = this;
        mAdView = findViewById(R.id.adViewcalculatenew);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        calculates = new ArrayList<>();
        txNo = findViewById(R.id.txNo3);
        rv = findViewById(R.id.rv4);
        progressBar = findViewById(R.id.progressoldcal);
        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        final String id_client = sharedPreferences.getString("client_id"," ");
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    final JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if(jsonArray.getJSONObject(0).has("cost")) {
                        for (int i=0;i<jsonArray.length();i++){
                            String cost = jsonArray.getJSONObject(i).getString("cost");
                            String calculation_id = jsonArray.getJSONObject(i).getString("calculation_id");
                            String image_calculation = jsonArray.getJSONObject(i).getString("image_calculation");
                            calculates.add(new calculatedate(cost, calculation_id, image_calculation));
                        }
                        LinearLayoutManager llm = new LinearLayoutManager(oldcalculationActivity.this);
                        rv.setLayoutManager(llm);
                        RVAdapteroldcal adapter = new RVAdapteroldcal(calculates,context);
                        rv.setAdapter(adapter);

                    }else{
//                         Toast.makeText(context, " "+, Toast.LENGTH_SHORT).show();
                        txNo.setVisibility(View.VISIBLE);
                        txNo.setText(jsonArray.getJSONObject(0).getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        oldcalculations oldcalculations = new oldcalculations(id_client,images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(oldcalculationActivity.this);
        queue.add(oldcalculations);
    }

    public void backicon(View view) {
        onBackPressed();
    }
    public void delete(View view) {
        Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
    }
    public class oldcalculations extends StringRequest {
        private final static String url="http://raihana-eg.com/site_api/api/myoldcalculation_api";
        private Map<String,String> params;

        public oldcalculations(String id_client, String lang, Response.Listener<String> listener){
            super(Method.POST,url,listener,null);
            params = new HashMap<>();
            params.put("id_client",id_client);
            params.put("lang",lang);
        }

        @Override
        protected Map<String, String> getParams(){
            return params;
        }
    }

}
