package com.mostafa.android.riahana;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by mostafa on 2/10/18.
 */

public class customArrayAdapter extends ArrayAdapter {
    public customArrayAdapter(@NonNull Context context, int resource) {
        super(context, 0);
    }

    public customArrayAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.customlist, parent, false);
        }
        TextView textView= (TextView) listItemView.findViewById(R.id.textCheckbox);
        final String title = (String) getItem(position);
        textView.setText(title);
       
        return listItemView;
        //return
    }
}
