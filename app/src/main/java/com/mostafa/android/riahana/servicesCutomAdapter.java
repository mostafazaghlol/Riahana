package com.mostafa.android.riahana;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mostafa on 2/22/18.
 */

public class servicesCutomAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<services> servicesDetalis;
    public servicesCutomAdapter(Context x, ArrayList<services> servicesName){
        mContext = x;
        servicesDetalis =servicesName;
    }

    @Override
    public int getCount() {
        return servicesDetalis.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            View list;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                list = new View(mContext);
                list = inflater.inflate(R.layout.custom_list, null);
                TextView textView = (TextView) list.findViewById(R.id.bt1);
                ImageView imageView = (ImageView) list.findViewById(R.id.profile_image9);
                textView.setText(servicesDetalis.get(position).servicesname);
                switch (servicesDetalis.get(position).colors){
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
                Picasso.with(mContext).load(servicesDetalis.get(position).imagesurl).into(imageView);
            } else {
                list = (View) convertView;
            }
            return list;
        }catch (Exception e){
            Log.e("hi "," "+e.getMessage());
        }
        return null;
    }
}
