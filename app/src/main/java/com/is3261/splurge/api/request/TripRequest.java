package com.is3261.splurge.api.request;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.is3261.splurge.Constant;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.Endpoint;
import com.is3261.splurge.api.GsonCollectionRequest;
import com.is3261.splurge.api.GsonRequest;
import com.is3261.splurge.api.Listener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.model.Trip;
import com.is3261.splurge.model.TripLocation;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class TripRequest {

    public static GsonRequest<Trip> post(Trip trip,String user_id, Listener<Trip> listener) {
        String url = Endpoint.POST_TRIP;
        Map<String, String> params = constructNewPostParams(trip);
        params.put("auth_token", SplurgeApi.getAuthToken());
        params.put("user_id", user_id);

        return new GsonRequest<>(Request.Method.POST, url, params, Trip.class, listener);
    }

    public static GsonCollectionRequest<Trip> loadTrips(String userId, CollectionListener<Trip> listener){
        String url = String.format(Endpoint.ALL_TRIPS, SplurgeApi.getAuthToken(),userId);
        Type type = new TypeToken<Collection<Trip>>(){}.getType();
        return new GsonCollectionRequest<>(Request.Method.GET, url, type, listener);
    }


    /**
     * to post to server
     * @param trip for persist
     * @return params
     */
    public static Map<String, String> constructNewPostParams(Trip trip) {
        if (trip == null) {
            return null;
        }

        DateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.US);
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", trip.getTitle());
        params.put("date", formatter.format(trip.getDate()));
        TripLocation location = trip.getTripLocation();
        params.put("latitude", String.valueOf(location.lat));
        params.put("longitude", String.valueOf(location.lng));
        params.put("street", location.street == null ? "" : location.street);
        params.put("city", location.city == null ? "" : location.city);
        params.put("state", location.state == null ? "" : location.state);
        params.put("country", location.country == null ? "" : location.country);
        params.put("zipcode", location.zipcode == null ? "" : location.zipcode);

        return params;
    }
}
