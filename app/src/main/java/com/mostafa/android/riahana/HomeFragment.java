package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import com.android.volley.AuthFailureError;
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

/**
 * Created by mostafa on 2/9/18.
 */

public class HomeFragment extends Fragment {
    View MyView;
    ArrayList<String> servicesname, ids, descriptions, imagesurl,colors;
    ListView listView;
    TextView textview;
    ProgressBar progressBar;
//    LinearLayout EyeLeftImage,RhinoplastyImage,InjectionImage,EmbellrskImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Intent i2 = new Intent(getActivity(),ServicesDetalisActivity.class);

        MyView = inflater.inflate(R.layout.home_fragment, container, false);
        progressBar = (ProgressBar) MyView.findViewById(R.id.progress10);
        servicesname = new ArrayList<String>();
        ids = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imagesurl = new ArrayList<String>();
        colors = new ArrayList<String>();
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
                        String Color = jsonArray.getJSONObject(i).getString("color");
                        servicesname.add(service_name);
                        ids.add(id_subservice);
//                        Toast.makeText(ServicesDetalisActivity.this, " "+ids, Toast.LENGTH_SHORT).show();
                        descriptions.add(description);
                        imagesurl.add(service_img);
                        colors.add(Color);

                    }
                    servicesCutomAdapter adapter = new servicesCutomAdapter(getActivity(), servicesname, imagesurl,colors);
                    listView = (ListView)MyView.findViewById(R.id.list_item2);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            i2.putExtra("serviceName",servicesname.get(i));
                            i2.putExtra("id",ids.get(i));
                            startActivity(i2);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        HomeRequest2 homeRequest2 = new HomeRequest2(images.lang, listener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(homeRequest2);

        return MyView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            view.setBackground(context.getResources().getDrawable(R.drawable.buttonblue));
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

    public class HomeRequest2 extends StringRequest {
        private final static String subServiceUrl = "http://raihana-eg.com/site_api/api/homeservice_api";
        private Map<String, String> params;


        public HomeRequest2(String lang, Response.Listener<String> listener) {
            super(Method.POST, subServiceUrl, listener, null);
            params = new HashMap<>();
            params.put("lang", lang);
        }

        @Override
        protected Map<String, String> getParams() {
            return params;
        }
    }

}