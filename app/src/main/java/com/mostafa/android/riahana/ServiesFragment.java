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
       final Intent i= new Intent(getContext(),Services.class);;
        EyeLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              i.putExtra("Title",getString(R.string.eyeleftprocess));
              i.putExtra("Image",R.drawable.placeholder);
              i.putExtra("information1","Place Holder");
              i.putExtra("information2","place Holder ");
              startActivity(i);
            }
        });
        RhinoplastyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Title",getString(R.string.rhinoplastv));
                i.putExtra("Image",R.drawable.placeholder);
                i.putExtra("information1","Place Holder");
                i.putExtra("information2","place Holder ");
                startActivity(i);
            }
        });
        InjectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Title",getString(R.string.theprocessoffateinjection));
                i.putExtra("Image",R.drawable.placeholder);
                i.putExtra("information1","Place Holder");
                i.putExtra("information2","place Holder ");
                startActivity(i);
            }
        });
        EmbellrskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("Title",getString(R.string.Embellrsktheear));
                i.putExtra("Image",R.drawable.placeholder);
                i.putExtra("information1","Place Holder");
                i.putExtra("information2","place Holder ");
                startActivity(i);
            }
        });


        return MyView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
