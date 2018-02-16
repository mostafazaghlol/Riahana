package com.mostafa.android.riahana;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mostafa on 2/11/18.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    List<person> persons;

    RVAdapter(List<person> persons){
        this.persons = persons;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customcardview, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.cash.setText(persons.get(i).cash);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        TextView cash;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cv.setCardBackgroundColor(Color.WHITE);
            cv.setRadius(100);
            personName = (TextView) itemView.findViewById(R.id.services_name);
            personAge = (TextView) itemView.findViewById(R.id.services_date);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            cash = (TextView)itemView.findViewById(R.id.services_cash);
        }
    }
}