package com.is3261.splurge.api;

import com.android.volley.Response;

/**
 * Created by junwen29 on 9/15/2015.
 */
public interface Listener<T> extends Response.ErrorListener {
    void onResponse(T object);
}
