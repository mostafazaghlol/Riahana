package com.mostafa.android.riahana;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by mostafa on 2/9/18.
 */

public class ServiesFragment extends Fragment {
    View MyView;
    ImageView EyeLeftImage,RhinoplastyImage,InjectionImage,EmbellrskImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MyView = inflater.inflate(R.layout.service_fragment, container, false);
        EyeLeftImage = (ImageView)MyView.findViewById(R.id.eyeLeftImage);
        RhinoplastyImage = (ImageView)MyView.findViewById(R.id.rhinoplastv);
        InjectionImage = (ImageView)MyView.findViewById(R.id.injection);
        EmbellrskImage = (ImageView)MyView.findViewById(R.id.Embellrsk);
        Intent i= null;
        EyeLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                i=new Intent()
            }
        });

        return MyView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
