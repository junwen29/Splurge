package com.is3261.splurge.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.FriendshipRequest;
import com.is3261.splurge.model.User;

/**
 * Created by junwen29 on 10/30/2015.
 *
 * to load friends
 */
public class LoadFriendsTask extends AsyncTask<Void,Void,Void> {

    private Context mContext;
    private CollectionListener<User> mListener;
    private String mUserId;

    public LoadFriendsTask(Context mContext, CollectionListener<User> mListener, String mUserId) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mUserId = mUserId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
            SplurgeApi.getInstance(mContext).enqueue(FriendshipRequest.loadFriends(mUserId, mListener));
        } catch (InterruptedException ignored) {
        }
        return null;
    }
}
