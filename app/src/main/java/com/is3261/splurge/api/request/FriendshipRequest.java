package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.EmptyRequest;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonCollectionRequest;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.model.User;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 10/18/2015.
 */
public class FriendshipRequest {

    public static EmptyRequest create(String user_id, String friend_email, EmptyListener listener) {
        String url = String.format(Endpoint.ADD_FRIEND, SplurgeApi.getAuthToken(), user_id, friend_email);
        return new EmptyRequest(Request.Method.POST, url, listener);
    }

    public static EmptyRequest approve(String user_id, String friend_id, EmptyListener listener) {
        String url = String.format(Endpoint.APPROVE_FRIEND, SplurgeApi.getAuthToken(), user_id, friend_id);
        return new EmptyRequest(Request.Method.PUT, url, listener);
    }

    public static EmptyRequest reject(String user_id, String friend_id, EmptyListener listener) {
        String url = String.format(Endpoint.REJECT_FRIEND, SplurgeApi.getAuthToken(), user_id, friend_id);
        return new EmptyRequest(Request.Method.DELETE, url, listener);
    }

    public static GsonCollectionRequest<User> loadRequestedFriends(String userId, CollectionListener<User> listener){
        String url = String.format(Endpoint.FRIENDS_REQUESTED, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<User>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static GsonCollectionRequest<User> loadFriendsForApproval(String userId, CollectionListener<User> listener){
        String url = String.format(Endpoint.FRIENDS_FOR_APPROVAL, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<User>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }

    public static GsonCollectionRequest<User> loadFriends(String userId, CollectionListener<User> listener){
        String url = String.format(Endpoint.ALL_FRIENDS, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<User>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
