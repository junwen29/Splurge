package com.is3261.splurge.api;

import com.android.volley.Response;

import java.util.Collection;

/**
 * Created by junwen29 on 9/17/2015.
 */
public interface CollectionListener<T> extends Response.ErrorListener {
    void onResponse(Collection<T> objects);
}
