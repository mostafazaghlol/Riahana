package com.mostafa.android.riahana;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mostafa on 2/22/18.
 */

public class CustomGridForServices extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> web;
    public CustomGridForServices(Context  c, ArrayList<String> mWeb){
        mContext=c;
        web=mWeb;
    }

    @Override
    public int getCount() {
        return web.size();
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
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridstyle, null);
            TextView textView = (TextView) grid.findViewById(R.id.servicetypeforgrid);
            ImageView imageView = (ImageView)grid.findViewById(R.id.faceImage);
            textView.setText(web.get(position));
//            Spinner spinner = (Spinner)grid.findViewById(R.id.spinner1);
//            spinner.setText
            imageView.setImageResource(R.drawable.face);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}

