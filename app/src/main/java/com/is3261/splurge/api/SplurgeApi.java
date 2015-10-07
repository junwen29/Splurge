package com.is3261.splurge.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.is3261.splurge.helper.OkHttpStack;
import com.is3261.splurge.helper.OwnerStore;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by junwen29 on 10/7/2015.
 */
public class SplurgeApi {

    static final String TAG = "BurppleApi";
    static final String ENDPOINT_CALL = "Endpoint";
    static final int DEFAULT_TIMEOUT = 20000; // 10 s
    static final int DEFAULT_MAX_RETRY = 0;

    private static SplurgeApi sInstance;

    private RequestQueue mRequestQueue;

    private String mAuthToken;
    private DefaultRetryPolicy mDefaultRetryPolicy = new DefaultRetryPolicy(DEFAULT_TIMEOUT, DEFAULT_MAX_RETRY,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public static SplurgeApi getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SplurgeApi(context.getApplicationContext());
        }
        sInstance.initAuthToken(context);

        return sInstance;
    }

    public static String getAuthToken() {
        return sInstance == null ? "" : sInstance.getToken();
    }

    public String getToken() {
        return mAuthToken;
    }


    public SplurgeApi(Context context) {
        mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack(context));
    }

    public void initAuthToken(Context context) {
        mAuthToken = new OwnerStore(context.getApplicationContext()).getAuthToken();
    }

    public void enqueue(Request<?> req) {
        req.setRetryPolicy(mDefaultRetryPolicy);
        mRequestQueue.add(req);

        Log.d(ENDPOINT_CALL, req.getUrl());
    }

    public void enqueue(Request<?> req, Object tag) {
        if (tag != null) {
            req.setTag(tag);

            Log.d(TAG, "Send request with tag: " + tag);
        }

        enqueue(req);
    }

    /**
     * Cancel all requests with the corresponding tag.
     * This should be called in onStop() method of the activity.
     *
     * @param tag Tag of requests to be cancelled.
     */
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
        Log.d(TAG, "Cancelling requests with tag: " + tag);
    }

    public static String acceptLanguage() {
        return Locale.getDefault().getLanguage() +
                "-" +
                Locale.getDefault().getCountry().toLowerCase(Locale.US);
    }

    public static int offsetFromUtc() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 1000;
    }
}
