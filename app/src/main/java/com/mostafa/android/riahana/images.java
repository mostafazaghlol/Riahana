package com.mostafa.android.riahana;

import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by mostafa on 2/11/18.
 */

public class images {
    static Uri imageUri;
    static int x=0;
    static int y=0;
    static int login=0;
    static String lang="1";
    static short activity=0;



    public static Uri getImageUri() {
        return imageUri;
    }

    public static void setImageUri(Uri imageUri) {
        images.imageUri = imageUri;
    }
}
