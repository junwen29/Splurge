package com.is3261.splurge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.base.RecyclerViewAdapter;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.ApproveFriendCard;

import java.util.ArrayList;

/**
 * Created by junwen29 on 10/25/2015.
 */
public class ApproveFriendsAdapter extends RecyclerViewAdapter<User> {
    public ApproveFriendsAdapter(ArrayList<User> mItems, Context context) {
        super(mItems, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_approve_friend, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User requestedFriend = getItem(position);
        ((ViewHolder) holder).update(requestedFriend);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ApproveFriendCard mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mCard = (ApproveFriendCard) itemView.findViewById(R.id.card_friend);
        }

        public void update(User friend){
            mCard.update(friend);
        }
    }
}
