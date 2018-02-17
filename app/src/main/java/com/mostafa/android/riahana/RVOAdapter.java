package com.mostafa.android.riahana;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mostafa on 2/12/18.
 */

public class RVOAdapter extends RecyclerView.Adapter<RVOAdapter.offerViewHolder> {
    List<offers> offers;

    RVOAdapter(List<offers> offer){
        this.offers= offer;
    }
    @Override
    public offerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customcardviewoffers, parent, false);
        offerViewHolder pvh = new offerViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(offerViewHolder holder, int position) {
        holder.offer.setText(offers.get(position).offer);
        holder.offerDetalis.setText(offers.get(position).offerDetalis);
        holder.offerPhoto.setImageResource(offers.get(position).photoid);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class offerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView offer;
        TextView offerDetalis;
        ImageView offerPhoto;

        offerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cv.setCardBackgroundColor(Color.WHITE);
            cv.setRadius(100);
            offer = (TextView) itemView.findViewById(R.id.offer);
            offerDetalis = (TextView) itemView.findViewById(R.id.offerdetalis);
            offerPhoto = (ImageView) itemView.findViewById(R.id.offerphoto);
//            cash = (TextView)itemView.findViewById(R.id.services_cash);
        }
    }
}