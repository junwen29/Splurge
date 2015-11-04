package com.is3261.splurge.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.ExpenseRequest;
import com.is3261.splurge.model.Expense;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class CreateExpenseTask extends AsyncTask<Void,Void,Void> {

    private Expense mExpense;
    private EmptyListener mListener;
    private Context mContext;

    public CreateExpenseTask(Expense mExpense, EmptyListener mListener, Context mContext) {
        this.mExpense = mExpense;
        this.mListener = mListener;
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
            SplurgeApi.getInstance(mContext).enqueue(
                    ExpenseRequest.create(
                            mExpense.getAmount(),
                            mExpense.getCurrency(),
                            Long.toString(mExpense.getSpender().id),
                            Long.toString(mExpense.getBorrower().id),
                            mListener));
        } catch (InterruptedException ignored) {
        }
        return null;
    }
}
