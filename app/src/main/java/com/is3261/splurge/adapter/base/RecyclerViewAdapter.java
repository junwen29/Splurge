package com.is3261.splurge.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by junwen29 on 10/21/2015.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ArrayList<T> mItems;
    protected Context mContext;

    public RecyclerViewAdapter(ArrayList<T> mItems, Context context) {
        this.mItems = mItems;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public void clear() {
        mItems.clear();
    }

    public void addAll(Collection<? extends T> items) {
        mItems.addAll(items);
    }

}
