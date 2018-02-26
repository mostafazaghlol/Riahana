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

public class newcalculationActivity extends AppCompatActivity {
    RecyclerView rv;
    ProgressBar progressBar;
    TextView txNo;
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
        setContentView(R.layout.activity_newcalculation);
        context = this;
        calculates = new ArrayList<>();
        txNo = findViewById(R.id.txNonewcal);
        progressBar = findViewById(R.id.progressnewcal);
        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        String id_client = sharedPreferences.getString("client_id"," ");
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if(jsonArray.getJSONObject(0).has("cost")) {
                        for (int i=0;i<jsonArray.length();i++){
                            String cost = jsonArray.getJSONObject(i).getString("cost");
                            String comment = jsonArray.getJSONObject(i).getString("comment");
                            String image_calculation = jsonArray.getJSONObject(i).getString("image_calculation");
                            calculates.add(new calculatedate(cost, comment, image_calculation));
                        }
                        rv = findViewById(R.id.rvnewcal);
                        LinearLayoutManager llm = new LinearLayoutManager(newcalculationActivity.this);
                        rv.setLayoutManager(llm);
                        RVAdapternewcal adapter = new RVAdapternewcal(calculates,context);
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

        newcalculationsRequest newcalculations = new newcalculationsRequest(id_client,images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(newcalculationActivity.this);
        queue.add(newcalculations);


    }

    public void backicon(View view) {
        onBackPressed();
    }

    public class newcalculationsRequest extends StringRequest {
        private final static String url="http://raihana-eg.com/site_api/api/mynewcalculation_api";
        private Map<String,String> params;

        public newcalculationsRequest(String id_client, String lang, Response.Listener<String> listener){
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
