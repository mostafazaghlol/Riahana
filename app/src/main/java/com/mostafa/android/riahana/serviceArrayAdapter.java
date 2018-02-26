package com.mostafa.android.riahana;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mostafa on 2/26/18.
 */

public class serviceArrayAdapter extends ArrayAdapter<services> {
    public serviceArrayAdapter(Activity context, ArrayList<services> listservice) {
        super(context, 0,listservice);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_list, parent, false);
        }
        //get object of the Array
        services getCurrentPlayer= getItem(position);
        TextView textView = listItemView.findViewById(R.id.bt1);
        ImageView imageView = listItemView.findViewById(R.id.profile_image9);
        RatingBar ratingBar = listItemView.findViewById(R.id.rating222);
        textView.setText(getCurrentPlayer.servicesname);
        switch (getCurrentPlayer.colors){
            case "#28792A":
                textView.setBackgroundResource(R.drawable.buttongreen);
                break;
            case "#F5922A":
                textView.setBackgroundResource(R.drawable.buttonorange);
                break;
            case "#29A9CE":
                textView.setBackgroundResource(R.drawable.buttonblue);
                break;
            case "#F8E620":
                textView.setBackgroundResource(R.drawable.buttonyellow);
                break;
            case "EA83AE":
                textView.setBackgroundResource(R.drawable.buttonpink);
                break;
            default:
                textView.setBackgroundResource(R.drawable.buttonorange);
        }
        Picasso.with(getContext()).load(getCurrentPlayer.imagesurl).into(imageView);
        if (getCurrentPlayer.countrate > 0) {
            ratingBar.setRating(getCurrentPlayer.countrate);
        } else {
            ratingBar.setRating(1);
        }
        return listItemView;
    }
}
