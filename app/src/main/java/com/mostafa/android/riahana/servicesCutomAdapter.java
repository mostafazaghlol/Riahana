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
    private ArrayList<String> servicename,imagersUrl;
    public servicesCutomAdapter(Context x, ArrayList<String> servicesName){
        mContext = x;
        servicename =servicesName;
    }
    public servicesCutomAdapter(Context x, ArrayList<String> servicesName,ArrayList<String> imageurl){
        mContext = x;
        servicename =servicesName;
        imagersUrl=imageurl;
    }
    @Override
    public int getCount() {
        return servicename.size();
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
                textView.setText(servicename.get(position));
                Picasso.with(mContext).load(imagersUrl.get(position)).into(imageView);
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
