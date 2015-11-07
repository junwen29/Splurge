package com.is3261.splurge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.base.RecyclerViewAdapter;
import com.is3261.splurge.model.Trip;
import com.is3261.splurge.view.TripCard;

import java.util.ArrayList;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class TripAdapter extends RecyclerViewAdapter<Trip> {

    public TripAdapter(ArrayList<Trip> mItems, Context context) {
        super(mItems, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_trip, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Trip trip = getItem(position);
        ((ViewHolder) holder).update(trip);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TripCard mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mCard = (TripCard) itemView.findViewById(R.id.card_trip);
        }

        public void update(Trip trip){
            mCard.update(trip);
        }
    }
}
