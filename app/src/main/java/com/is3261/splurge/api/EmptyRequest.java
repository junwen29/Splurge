package com.is3261.splurge.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by junwen29 on 10/22/2015.
 */
public class EmptyRequest extends Request<Void> {

    private final EmptyListener mListener;
    private final Map<String, String> mParams;

    public EmptyRequest(int method, String url, EmptyListener listener) {
        this(method, url, null, listener);
    }

    public EmptyRequest(int method, String url, Map<String, String> params, EmptyListener listener) {
        super(method, url, listener);
        mListener = listener;
        mParams = params;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return (mParams == null) ? super.getParams() : mParams;
    }

    @Override
    protected Response<Void> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(Void aVoid) {
        if (mListener != null) mListener.onResponse();
    }
}
