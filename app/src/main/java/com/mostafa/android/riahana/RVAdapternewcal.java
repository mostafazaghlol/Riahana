package com.mostafa.android.riahana;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mostafa on 2/11/18.
 */

public class RVAdapternewcal extends RecyclerView.Adapter<RVAdapternewcal.PersonViewHolder> {
    List<calculatedate> calculates;
    private Context mContext;
    RVAdapternewcal(List<calculatedate> persons, Context context){
        this.calculates = persons;
        mContext = context;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customcardviewnewcal, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.Cost.setText("cost : "+calculates.get(i).cost);
        personViewHolder.id.setText("id : "+calculates.get(i).id);
        Picasso.with(mContext).load(calculates.get(i).image).into(personViewHolder.calphoto);
    }

    @Override
    public int getItemCount() {
        return calculates.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView Cost;
        TextView id;
        ImageView calphoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv9);
            cv.setCardBackgroundColor(Color.WHITE);
            cv.setRadius(100);
            Cost = (TextView) itemView.findViewById(R.id.cost2);
            id= (TextView) itemView.findViewById(R.id.idtext2);
            calphoto = (ImageView) itemView.findViewById(R.id.person_photo2);
        }
    }
}