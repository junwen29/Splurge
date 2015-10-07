package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonRequest;
import com.is3261.splurge.api.Listener;
import com.is3261.splurge.model.Owner;

import java.util.Map;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class SignupRequest {

    private static GsonRequest<Owner> signup(Map<String, String> params, Listener<Owner> listener) {
        String url = String.format(Endpoint.SIGNUP);
        return new GsonRequest<Owner>(Request.Method.POST, url, params, Owner.class, listener);
    }

    public static GsonRequest<Owner> email(Owner owner, Listener<Owner> listener) {
        Map<String, String> params = owner.constructSignupParams();
        return signup(params, listener);
    }
}
