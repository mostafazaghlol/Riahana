package com.mostafa.android.riahana;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mostafa on 2/10/18.
 */

public class CustomDialogClass extends Dialog implements  RatingBar.OnRatingBarChangeListener  {

    public Activity c;
    String message, id_client;
    private RatingBar ratingBar;
    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(this);
        final SharedPreferences sharedPreferences = c.getSharedPreferences("pref", 0);
        id_client = sharedPreferences.getString("client_id", "");

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        message = jsonObject1.getString("message");
                        Toast.makeText(c, " " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        RatingRequest ratingRequest = new RatingRequest(images.lang, id_client, Services.id_services, String.valueOf(v), listener);
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(ratingRequest);


    }

    public class RatingRequest extends StringRequest {
        private final static String url = "http://raihana-eg.com/site_api/api/add_rate";
        private Map<String, String> params;

        public RatingRequest(String lang, String id_client, String id_service, String count_rate, Response.Listener<String> listener) {
            super(Method.POST, url, listener, null);
            params = new HashMap<>();
            params.put("lang", lang);
            params.put("id_client", id_client);
            params.put("count_rate", count_rate);
            params.put("id_service", id_service);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
}