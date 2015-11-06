package com.is3261.splurge.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;

import com.is3261.splurge.api.Listener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.TripRequest;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.Trip;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class CreateTripTask extends AsyncTask<Void,Void,Void> {

    private Trip mTrip;
    private Listener<Trip> mListener;
    private Context mContext;

    public CreateTripTask(Trip mTrip, Context mContext, Listener<Trip> mListener) {
        this.mTrip = mTrip;
        this.mListener = mListener;
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
            String user_id = new OwnerStore(mContext).getOwnerId();
            SplurgeApi.getInstance(mContext).enqueue(TripRequest.post(mTrip, user_id, mListener));

        } catch (InterruptedException ignored) {
        }
        return null;
    }
}
