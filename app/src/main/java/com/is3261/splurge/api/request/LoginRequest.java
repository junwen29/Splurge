package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonRequest;
import com.is3261.splurge.api.Listener;
import com.is3261.splurge.model.Owner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class LoginRequest {

    private static GsonRequest<Owner> login(Map<String, String> params, Listener<Owner> listener) {
        String url = String.format(Endpoint.LOGIN);
        return new GsonRequest<>(Request.Method.POST, url, params, Owner.class, listener);
    }

    public static GsonRequest<Owner> email(String email, String password, Listener<Owner> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return login(params, listener);
    }
}
