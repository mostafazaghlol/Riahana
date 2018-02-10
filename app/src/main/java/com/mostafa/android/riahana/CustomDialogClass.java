package com.mostafa.android.riahana;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by mostafa on 2/10/18.
 */

public class CustomDialogClass extends Dialog implements  RatingBar.OnRatingBarChangeListener  {

    public Activity c;
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
        ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(this);

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

        Toast.makeText(c, "the rating is "+String.valueOf(v), Toast.LENGTH_SHORT).show();

    }
}