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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComfirmedReservationsActivity extends AppCompatActivity {
    RecyclerView rv;
    private List<person> persons;
    private Context context;
    ProgressBar progressBar;
    TextView txNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_comfiremd_reservations);
        context = this;
        persons = new ArrayList<>();
        txNo = (TextView)findViewById(R.id.textNo);
        progressBar = (ProgressBar)findViewById(R.id.progress3);
        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        final String id_client = sharedPreferences.getString("client_id"," ");
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if(jsonArray.getJSONObject(0).has("date_time")) {
                        for (int i=0;i<jsonArray.length();i++){
                            String service_name = jsonArray.getJSONObject(i).getString("service_name");
                            String date_time = jsonArray.getJSONObject(i).getString("date_time");
                            String service_img = jsonArray.getJSONObject(i).getString("service_img");
                            persons.add(new person(service_name, date_time, service_img));
                    }
                        rv = (RecyclerView)findViewById(R.id.rv);
                        LinearLayoutManager llm = new LinearLayoutManager(ComfirmedReservationsActivity.this);
                    rv.setLayoutManager(llm);
                    RVAdapter2 adapter = new RVAdapter2(persons,context);
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

        comfirmedReservartion comfirmedReservartion = new comfirmedReservartion(id_client,images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(ComfirmedReservationsActivity.this);
        queue.add(comfirmedReservartion);


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

    public void backicon(View view) {
        onBackPressed();
    }

    public class comfirmedReservartion extends StringRequest{
        private final static String url="http://raihana-eg.com/site_api/api/myoldbooking_api";
        private Map<String,String> params;

        public comfirmedReservartion(String id_client, String lang, Response.Listener<String> listener){
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
