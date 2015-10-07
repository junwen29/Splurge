package com.is3261.splurge.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 10/7/2015.
 */
public class Owner extends User {
    @SerializedName("auth_token")
    private String authToken;

    private transient String password;

    public Owner() {
        super();
    }

    public Owner(Owner source) {
        this();
        update(source);
    }

    public void update(Owner source) {
        username = source.username;
        authToken = source.authToken;
    }

    public Map<String, String> constructSignupParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("username", username);
        if (!TextUtils.isEmpty(password)) params.put("password", password);

        return params;
    }
}
