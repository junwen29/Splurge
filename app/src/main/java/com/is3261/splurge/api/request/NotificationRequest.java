package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonCollectionRequest;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.model.Notification;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by junwen29 on 11/8/2015.
 */
public class NotificationRequest {

    public static GsonCollectionRequest<Notification> loadAll(String userId, CollectionListener<Notification> listener){
        Type type = new TypeToken<Collection<Notification>>(){}.getType();
        String url = String.format(Endpoint.NOTIFICATIONS, SplurgeApi.getAuthToken(), userId);
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }
}
