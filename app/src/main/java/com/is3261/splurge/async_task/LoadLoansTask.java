package com.is3261.splurge.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.ExpenseRequest;
import com.is3261.splurge.model.Expense;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class LoadLoansTask extends AsyncTask<Void,Void,Void> {

    private Context mContext;
    private CollectionListener<Expense> mListener;
    private String mUserId;

    public LoadLoansTask(Context mContext, CollectionListener<Expense> mListener, String mUserId) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mUserId = mUserId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
            SplurgeApi.getInstance(mContext).enqueue(ExpenseRequest.lends(mUserId, mListener));
        } catch (InterruptedException ignored) {
        }
        return null;
    }
}
