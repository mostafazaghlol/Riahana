package com.mostafa.android.riahana;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by mostafa on 3/4/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.admob_id));

    }
}
