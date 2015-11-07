package com.is3261.splurge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.DebtActivity;
import com.is3261.splurge.activity.ProfileActivity;
import com.is3261.splurge.adapter.base.RecyclerViewAdapter;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Avatar;
import com.is3261.splurge.model.Notification;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by junwen29 on 11/8/2015.
 */
public class NotificationAdapter extends RecyclerViewAdapter<Notification> {

    private static final String EXPENSE = "expense";
    private static final String FRIENDSHIP = "friendship";

    public NotificationAdapter(ArrayList<Notification> mItems, Context context) {
        super(mItems, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Notification notification = getItem(position);

        // update the views
        ((ViewHolder)holder).mTitle.setText(notification.getItemName());
        Date time = notification.getCreatedAt();
        String timeAgo = SplurgeHelper.getTimeAgo(mContext, time.getTime(), System.currentTimeMillis());
        ((ViewHolder) holder).mTime.setText(timeAgo);
        ((ViewHolder)holder).mMessage.setText(notification.getMessage());

        //set different on click functions
        switch (notification.getItemType()){
            case FRIENDSHIP:
                // profile page
                ((ViewHolder)holder).mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProfileActivity.class);
                        intent.putExtra("deal_id", notification.getItemId());
                        mContext.startActivity(intent);
                    }
                });
                ((ViewHolder)holder).mAvatar.setImageResource(Avatar.getRandomAvatar().getDrawableId());
                ((ViewHolder)holder).mAvatar.setBackground(null);
                break;
            case EXPENSE:
                // debt page
                ((ViewHolder)holder).mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DebtActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                ((ViewHolder)holder).mAvatar.setImageResource(Avatar.getRandomAvatar().getDrawableId());
                ((ViewHolder)holder).mAvatar.setBackground(null);
                break;
            default:
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mAvatar;
        TextView mTitle;
        TextView mTime;
        TextView mMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mMessage = (TextView) itemView.findViewById(R.id.message);
        }
    }
}
