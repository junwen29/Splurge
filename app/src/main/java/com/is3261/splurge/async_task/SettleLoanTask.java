package com.is3261.splurge.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.ExpenseRequest;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class SettleLoanTask extends AsyncTask<Void,Void,Void> {
    private String mExpenseId;
    private Context mContext;
    private EmptyListener mListener;

    public SettleLoanTask(String mExpenseId, Context mContext, EmptyListener mListener) {
        this.mExpenseId = mExpenseId;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
            SplurgeApi.getInstance(mContext).enqueue(ExpenseRequest.settleUp(mExpenseId, mListener));
        } catch (InterruptedException ignored) {
        }
        return null;
    }
}
