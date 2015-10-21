package com.is3261.splurge.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.is3261.splurge.adapter.base.RecyclerViewAdapter;
import com.is3261.splurge.model.User;

import java.util.ArrayList;

/**
 * Created by junwen29 on 10/21/2015.
 */
public class FriendsAdapter extends RecyclerViewAdapter<User> {

    public FriendsAdapter(ArrayList<User> mItems) {
        super(mItems);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
