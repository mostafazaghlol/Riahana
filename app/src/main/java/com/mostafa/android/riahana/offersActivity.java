package com.mostafa.android.riahana;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

public class offersActivity extends AppCompatActivity {
    RecyclerView rv;
    private List<offers> offersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_offers);
        offersList= new ArrayList<>();
        offersList.add(new offers(getString(R.string.fackoffer),getString(R.string.FakeDate),R.drawable.picicon));
        offersList.add(new offers(getString(R.string.fackoffer),getString(R.string.FakeDate),R.drawable.picicon));
        offersList.add(new offers(getString(R.string.fackoffer),getString(R.string.FakeDate),R.drawable.picicon));
        rv = (RecyclerView)findViewById(R.id.rv2);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        RVOAdapter adapter = new RVOAdapter(offersList);
        rv.setAdapter(adapter);


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
}
