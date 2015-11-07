package com.is3261.splurge.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.TripRequest;
import com.is3261.splurge.model.Trip;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class LoadTripsTask extends AsyncTask<Void,Void,Void> {

    private Context mContext;
    private CollectionListener<Trip> mListener;
    private String mUserId;

    public LoadTripsTask(Context mContext, CollectionListener<Trip> mListener, String mUserId) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mUserId = mUserId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
            SplurgeApi.getInstance(mContext).enqueue(TripRequest.loadTrips(mUserId, mListener));
        } catch (InterruptedException ignored) {
        }
        return null;
    }
}
