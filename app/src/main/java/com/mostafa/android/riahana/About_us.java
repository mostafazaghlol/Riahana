package com.mostafa.android.riahana;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class About_us extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        textView = (TextView)findViewById(R.id.text11);
        progressBar = (ProgressBar)findViewById(R.id.progress11);
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject respons = jsonArray.getJSONObject(i);
                        String about_site = respons.getString("about_site");
                        textView.setText(Html.fromHtml(about_site));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        About_us_Request about_us_request = new About_us_Request(images.lang,responseListener);
        RequestQueue queue = Volley.newRequestQueue(About_us.this);
        queue.add(about_us_request);

    }

    public void backicon(View view) {
        onBackPressed();
    }


    public class About_us_Request extends StringRequest {
        private static final String LOGIN_REQUEST_URL = "http://raihana-eg.com/site_api/api/Aboutus_api";
        private Map<String, String> params;

        public About_us_Request(String lang, Response.Listener<String> listener) {
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("lang",lang);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

}
