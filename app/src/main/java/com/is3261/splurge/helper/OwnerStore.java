package com.is3261.splurge.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.is3261.splurge.BuildConfig;
import com.is3261.splurge.model.Owner;

import java.util.Map;

/**
 * Created by junwen29 on 10/6/2015.
 */
public class OwnerStore {
    public static final String TAG = "OwnerStore";
    public static final String OWNER_PREFERENCE = "OWNER_PREF";
    public static final String OWNER_ID = "OWNER_ID"; //OWNER_ID is string
    public static final String USERNAME = "USERNAME";
    public static final String EMAIL = "EMAIL";
    public static final String AUTH_TOKEN = "AUTH_TOKEN";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private boolean mBatchEdit;
    private Context mApplicationContext;
    private String username;

    public OwnerStore(Context context) {
        mApplicationContext = context.getApplicationContext();
        mSharedPreferences = mApplicationContext.getSharedPreferences(OWNER_PREFERENCE, Activity.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public String getUsername() {
        return mSharedPreferences.getString(USERNAME, null);
    }

    public String getEmail(){
        return mSharedPreferences.getString(EMAIL, null);
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, null);
    }

    public String getOwnerId() {
        return Long.toString(mSharedPreferences.getLong(OWNER_ID, 0));
    }

    public void setAuthToken(String token) {
        mEditor.putString(AUTH_TOKEN, token).commit();
    }

    public void clearAuthToken() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(AUTH_TOKEN);
        editor.apply();
    }

    public void setBatchEdit(boolean on) {
        mBatchEdit = on;
    }

    public void setUserId(long id) {
        mEditor.putLong(OWNER_ID, id);
        if (!mBatchEdit) mEditor.commit();
    }

    public void setEmail(String email) {
        mEditor.putString(EMAIL, email);
        if(!mBatchEdit) mEditor.commit();
    }

    public boolean commit() {
        return mEditor.commit();
    }

    public void storeAccountInfo(Owner owner) {
        setBatchEdit(true);
        if (!TextUtils.isEmpty(owner.getAuthToken())) setAuthToken(owner.getAuthToken());
        setUserId(owner.id);
        setEmail(owner.getEmail());
        setUsername(owner.getUsername());
        commit();

        if (BuildConfig.DEBUG) logAllEntries();
    }

    public void logAllEntries() {
        Map<String, ?> entries = mSharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : entries.entrySet()) {
            Log.d(TAG, entry.getKey() + " : " + entry.getValue());
        }
    }

    public void clear() {
        mEditor.clear().commit();
    }

    public void setUsername(String username) {
        mEditor.putString(USERNAME, username);
        if(!mBatchEdit) mEditor.commit();
    }
}
