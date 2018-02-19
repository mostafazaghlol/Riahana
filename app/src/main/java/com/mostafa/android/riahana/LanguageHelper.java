package com.mostafa.android.riahana;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by mostafa on 2/20/18.
 */

public class LanguageHelper {
    public static void changeLocale(Resources res,String local){
        Configuration config;
        config = new Configuration(res.getConfiguration());

        switch (local){
            case "ar":
                config.locale = new Locale("ar");
                break;
                default:
                    config.locale = Locale.ENGLISH;
                    break;
        }
        res.updateConfiguration(config,res.getDisplayMetrics());
    }
}
