package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.EmptyRequest;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonCollectionRequest;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.model.Expense;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class ExpenseRequest {

    public static EmptyRequest create(String amount, String currency,
                                      String spender_id, String borrower_id, EmptyListener listener) {
        String url = String.format(Endpoint.CREATE_EXPENSE, SplurgeApi.getAuthToken(), amount, currency, spender_id, borrower_id);
        return new EmptyRequest(Request.Method.POST, url, listener);
    }

    public static GsonCollectionRequest<Expense> debts (String userId, CollectionListener<Expense> listener){
        String url = String.format(Endpoint.DEBTS, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<Expense>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static GsonCollectionRequest<Expense> allDebts (String userId, CollectionListener<Expense> listener){
        String url = String.format(Endpoint.ALL_DEBTS, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<Expense>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static GsonCollectionRequest<Expense> lends (String userId, CollectionListener<Expense> listener){
        String url = String.format(Endpoint.LENDS, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<Expense>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static GsonCollectionRequest<Expense> allLends (String userId, CollectionListener<Expense> listener){
        String url = String.format(Endpoint.ALL_LENDS, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<Expense>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static EmptyRequest settleUp(String expense_id, EmptyListener listener) {
        String url = String.format(Endpoint.SETTLE_UP, SplurgeApi.getAuthToken(), expense_id);
        return new EmptyRequest(Request.Method.PUT, url, listener);
    }
}
