package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.EmptyRequest;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonRequest;
import com.is3261.splurge.api.Listener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.model.Friendship;

/**
 * Created by junwen29 on 10/18/2015.
 */
public class FriendshipRequest {

    public static EmptyRequest create(String user_id, String friend_email, EmptyListener listener) {

        String url = String.format(Endpoint.ADD_FRIEND, SplurgeApi.getAuthToken(), user_id, friend_email );
        return new EmptyRequest(Request.Method.POST, url, listener);
    }
}
