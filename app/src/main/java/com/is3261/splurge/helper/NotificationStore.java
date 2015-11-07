package com.is3261.splurge.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by junwen29 on 11/7/2015.
 */
public class NotificationStore {

    public static final String NAME = "splurge.app.NOTIFICATION_STORE";
    public static final String UNREAD_COUNT = "unread_count";
    public static final String UNREAD_MESSAGES = "unread_messages";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public NotificationStore(Context context) {
        mSharedPreferences = context.getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void setUnreadMessages(Set<String> messages) {
        mEditor.putStringSet(UNREAD_MESSAGES, messages);
        mEditor.commit();
    }

    public Set<String> getUnreadMessages() {
        return mSharedPreferences.getStringSet(UNREAD_MESSAGES, null);
    }

    public void setUnreadCount(int count) {
        mEditor.putInt(UNREAD_COUNT, count);
        mEditor.commit();
    }

    public int getUnreadCount() {
        return mSharedPreferences.getInt(UNREAD_COUNT, 0);
    }

    public void clear() {
        mEditor.clear().commit();
    }
}
