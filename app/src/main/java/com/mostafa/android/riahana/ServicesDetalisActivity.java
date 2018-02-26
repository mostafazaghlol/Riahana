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
import android.widget.ListView;
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
import java.util.Map;

public class ServicesDetalisActivity extends AppCompatActivity {
    ArrayList<services> servicesDetalis;
    ListView listView;
    TextView textview,tx;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_services_detalis);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        servicesDetalis = new ArrayList<services>();
        textview = (TextView) findViewById(R.id.textServices);
        tx = (TextView)findViewById(R.id.txnoser);
        Intent i = getIntent();
        textview.setText(i.getStringExtra("serviceName"));
        final Intent i2 = new Intent(this, Services.class);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    tx.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if(jsonArray.getJSONObject(0).has("service_name")){
                        for (int i = 0; i < jsonArray.length(); i++) {
                        String service_name = jsonArray.getJSONObject(i).getString("service_name");
                        String id_subservice = jsonArray.getJSONObject(i).getString("id_subservice");
                        String description = jsonArray.getJSONObject(i).getString("description");
                        String service_img = jsonArray.getJSONObject(i).getString("service_img");
                        String color= jsonArray.getJSONObject(i).getString("color");
                        servicesDetalis.add(new services(service_name,id_subservice,description,service_img,color));
                    }
                    }else{
                        tx.setVisibility(View.VISIBLE);
                        String message=jsonArray.getJSONObject(0).getString("message");
                        tx.setText(message);
                    }

                    serviceArrayAdapter adapter = new serviceArrayAdapter(ServicesDetalisActivity.this,servicesDetalis);
                    listView = (ListView) findViewById(R.id.list_item);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
//                                i2.putExtra("imageurl",imagesurl.get(position));
//                                i2.putExtra("description",descriptions.get(position));
//                                i2.putExtra("name",servicesname.get(position));
                                i2.putExtra("id",servicesDetalis.get(position).ids);
                                ServicesDetalisActivity.this.startActivity(i2);
//                                finish();

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ServiceRequest2 serviceRequest2 = new ServiceRequest2(images.lang, i.getStringExtra("id"), listener);
        RequestQueue queue = Volley.newRequestQueue(ServicesDetalisActivity.this);
        queue.add(serviceRequest2);

    }

    public void backIcon(View view) {
        onBackPressed();
    }
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

    public class ServiceRequest2 extends StringRequest {
        private final static String subServiceUrl = "http://raihana-eg.com/site_api/api/subservice_api";
        private Map<String, String> params;


        public ServiceRequest2(String lang, String id, Response.Listener<String> listener) {
            super(Method.POST, subServiceUrl, listener, null);
            params = new HashMap<>();
            params.put("lang", lang);
            params.put("id_services", id);
        }

        @Override
        protected Map<String, String> getParams() {
            return params;
        }
    }

}
