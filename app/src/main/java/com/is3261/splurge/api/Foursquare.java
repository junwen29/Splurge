package com.is3261.splurge.api;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.is3261.splurge.Constant;
import com.is3261.splurge.model.TripLocation;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class Foursquare {
    public static final String CLIENT_ID = "VE1ZCE3VAXBI5GXJOCZFYJSHGQXEMLC1OZT3YU2BDHA2OXHL";
    public static final String CLIENT_SECRET = "2KHPP0CL5TWSZOQDOUWUUWA0CVXTUCLLLR5DZT0OM55KTXBB";
    public static final String API_VERSION = "20130815";
    public static final String BASE_URL = "https://api.foursquare.com";

    public static interface FoursquareListener {
        public void onSuccess(List<TripLocation> location);

        public void onFailed();
    }

    private RequestQueue mRequestQueue;

    public Foursquare(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }


    public void fetch(String query, String ll, FoursquareListener listener) {
        List<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("client_id", CLIENT_ID));
        params.add(new Pair<>("client_secret", CLIENT_SECRET));
        params.add(new Pair<>("v", API_VERSION));
        params.add(new Pair<>("limit", String.valueOf(50)));
        params.add(new Pair<>("ll", ll));
        params.add(new Pair<>("intent", "checkin"));
        if (!TextUtils.isEmpty(query)) params.add(new Pair<>("query", query));
    }

    public void fetch(String query, String ll, CollectionListener<TripLocation> listener) {
        String url = BASE_URL + "/v2/venues/search?";

        url += "client_id=" + CLIENT_ID;
        url += "&client_secret=" + CLIENT_SECRET;
        url += "&v=" + API_VERSION;
        url += "&limit=50";
        if (ll == null) {
            ll = "1.291728,103.849369";
        }
        url += "&ll=" + ll;
        url += "&intent=checkin";
        if (!TextUtils.isEmpty(query)) url += "&query=" + query;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        mRequestQueue.add(new GsonCollectionRequest<TripLocation>(Request.Method.GET, url, null, headers, null, listener) {
            @SuppressWarnings("unchecked")
            @Override
            protected Response<Collection<TripLocation>> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                    Type type = new TypeToken<Collection<TripLocation>>() {
                    }.getType();
                    Gson gson = new GsonBuilder().setDateFormat(Constant.DATE_FORMAT)
                            .registerTypeAdapter(TripLocation.class, new FoursquareDeserializer())
                            .create();

                    JsonElement je = new JsonParser().parse(json)
                            .getAsJsonObject()
                            .get("response")
                            .getAsJsonObject()
                            .get("venues");

                    return Response.success((Collection<TripLocation>) gson.fromJson(je, type),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }
        });
    }

    public static class FoursquareDeserializer implements JsonDeserializer<TripLocation> {
        @Override
        public TripLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jo = json.getAsJsonObject();
            TripLocation location = new TripLocation();
            location.name = jo.get("name").getAsString();
            JsonObject address = jo.getAsJsonObject("location");
            location.lat = address.get("lat").getAsFloat();
            location.lng = address.get("lng").getAsFloat();
            if (address.has("address")) location.street = address.get("address").getAsString();
            if (address.has("city")) location.city = address.get("city").getAsString();
            if (address.has("state")) location.state = address.get("state").getAsString();
            if (address.has("country")) location.country = address.get("country").getAsString();
            if (address.has("postalCode")) location.zipcode = address.get("postalCode").getAsString();

            return location;
        }
    }
}
