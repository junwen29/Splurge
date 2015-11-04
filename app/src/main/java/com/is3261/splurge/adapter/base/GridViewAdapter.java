package com.is3261.splurge.adapter.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by junwen29 on 11/4/2015.
 */
public abstract class GridViewAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mItems;
    protected Context mContext;

    public GridViewAdapter(ArrayList<T> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    public void addAll(Collection<? extends T> items) {
        mItems.addAll(items);
    }

    public void clear() {
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    public ArrayList<T> getItems() {
        return mItems;
    }

}
