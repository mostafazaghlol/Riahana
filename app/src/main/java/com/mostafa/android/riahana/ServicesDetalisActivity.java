package com.mostafa.android.riahana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.Map;

public class ServicesDetalisActivity extends AppCompatActivity {
    ArrayList<String> servicesname, ids, descriptions, imagesurl;
    ListView listView;
    TextView textview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_detalis);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        servicesname = new ArrayList<String>();
        ids = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imagesurl = new ArrayList<String>();
        textview = (TextView) findViewById(R.id.textServices);
        Intent i = getIntent();
        textview.setText(i.getStringExtra("serviceName"));
        final Intent i2 = new Intent(this, Services.class);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String service_name = jsonArray.getJSONObject(i).getString("service_name");
                        String id_subservice = jsonArray.getJSONObject(i).getString("id_subservice");
                        String description = jsonArray.getJSONObject(i).getString("description");
                        String service_img = jsonArray.getJSONObject(i).getString("service_img");
                        servicesname.add(service_name);
                        ids.add(id_subservice);
//                        Toast.makeText(ServicesDetalisActivity.this, " "+ids, Toast.LENGTH_SHORT).show();
                        descriptions.add(description);
                        imagesurl.add(service_img);

                    }
                    servicesCutomAdapter adapter = new servicesCutomAdapter(ServicesDetalisActivity.this, servicesname, imagesurl);
                    listView = (ListView) findViewById(R.id.list_item);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                                i2.putExtra("imageurl",imagesurl.get(position));
                                i2.putExtra("description",descriptions.get(position));
                                i2.putExtra("name",servicesname.get(position));
                                i2.putExtra("id",ids.get(position));
                                ServicesDetalisActivity.this.startActivity(i2);
                                finish();

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
