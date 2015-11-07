package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.EmptyRequest;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.SplurgeApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 11/7/2015.
 */
public class SettingsRequest {

    public static EmptyRequest registerDeviceToken(String token, EmptyListener listener) {
        String url = Endpoint.REGISTER_DEVICE;
        Map<String, String> params = new HashMap<String, String>();
        params.put("auth_token", SplurgeApi.getAuthToken());
        params.put("device_token", token);
        params.put("device_type", "android_gcm");

        return new EmptyRequest(Request.Method.POST, url, params, listener);
    }

    public static EmptyRequest unregisterDeviceToken(String token, EmptyListener listener) {
        String url = String.format(Endpoint.UNREGISTER_DEVICE, SplurgeApi.getAuthToken(), token);
        return new EmptyRequest(Request.Method.DELETE, url, listener);
    }
}
