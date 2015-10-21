package com.is3261.splurge.adapter.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by junwen29 on 10/21/2015.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ArrayList<T> mItems;

    public RecyclerViewAdapter(ArrayList<T> mItems) {
        this.mItems = mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }
}
