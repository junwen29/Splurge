package com.is3261.splurge.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class Trip {

    @SerializedName("id")
    public long id;
    @SerializedName("title")
    private String title;
    @SerializedName("date")
    private Date date;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("state")
    private String state;
    @SerializedName("street")
    private String street;
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("latitude")
    public float lat;
    @SerializedName("longitude")
    public float lng;
    @SerializedName("user")
    private User user;

    private TripLocation tripLocation;

    public Trip(long id) {
        this.id = id;
    }

    public void setTripLocation(TripLocation tripLocation) {
        this.tripLocation = tripLocation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TripLocation getTripLocation() {
        return tripLocation;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
