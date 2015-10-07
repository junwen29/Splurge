package com.is3261.splurge.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by junwen29 on 10/6/2015.
 */
public class OwnerStore {
    public static final String TAG = "OwnerStore";
    public static final String OWNER_PREFERENCE = "OWNER_PREF";
    public static final String EMAIL = "EMAIL";
    public static final String AUTH_TOKEN = "AUTH_TOKEN";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private boolean mBatchEdit;

    public OwnerStore(Context context) {
        mSharedPreferences = context.getSharedPreferences(OWNER_PREFERENCE, Activity.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void setAuthToken(String token) {
        mEditor.putString(AUTH_TOKEN, token).commit();
    }

    public void clearAuthToken() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(AUTH_TOKEN);
        editor.apply();
    }

}
