package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by mostafa on 2/9/18.
 */

public class ServiesFragment extends Fragment {
    View MyView;
    LinearLayout EyeLeftImage,RhinoplastyImage,InjectionImage,EmbellrskImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MyView = inflater.inflate(R.layout.service_fragment, container, false);
        try {
            EyeLeftImage = (LinearLayout) MyView.findViewById(R.id.eyeLeftImage);
            RhinoplastyImage = (LinearLayout) MyView.findViewById(R.id.rhinoplastv);
            InjectionImage = (LinearLayout) MyView.findViewById(R.id.injection);
            EmbellrskImage = (LinearLayout) MyView.findViewById(R.id.Embellrsk);
            final Intent i = new Intent(getContext(), Services.class);
            ;
            EyeLeftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i.putExtra("Title", getString(R.string.eyeleftprocess));
                    i.putExtra("Image", R.drawable.face);
                    i.putExtra("information1", "Place Holder");
                    i.putExtra("information2", "place Holder ");
                    startActivity(i);
                }
            });
            RhinoplastyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i.putExtra("Title", getString(R.string.rhinoplastv));
                    i.putExtra("Image", R.drawable.face);
                    i.putExtra("information1", "Place Holder");
                    i.putExtra("information2", "place Holder ");
                    startActivity(i);
                }
            });
            InjectionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i.putExtra("Title", getString(R.string.theprocessoffateinjection));
                    i.putExtra("Image", R.drawable.face);
                    i.putExtra("information1", "Place Holder");
                    i.putExtra("information2", "place Holder ");
                    startActivity(i);
                }
            });
            EmbellrskImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i.putExtra("Title", getString(R.string.Embellrsktheear));
                    i.putExtra("Image", R.drawable.face);
                    i.putExtra("information1", "Place Holder");
                    i.putExtra("information2", "place Holder ");
                    startActivity(i);
                }
            });
        }catch (Exception e){
            Log.e("ServiesFragment",e.getMessage());
        }

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
}
