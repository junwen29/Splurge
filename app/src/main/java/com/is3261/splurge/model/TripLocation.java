package com.is3261.splurge.model;

import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.is3261.splurge.helper.SplurgeHelper;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class TripLocation implements Parcelable {
    @SerializedName("place")
    public String name;
    @SerializedName("street")
    public String street;
    @SerializedName("neighbourhood")
    public String neighbourhood;
    @SerializedName("city")
    public String city;
    @SerializedName("state")
    public String state;
    @SerializedName("country")
    public String country;
    @SerializedName("zipcode")
    public String zipcode;
    @SerializedName("latitude")
    public float lat;
    @SerializedName("longitude")
    public float lng;

    public TripLocation() {
        this.name = "";
        this.street = "";
        this.neighbourhood = "";
        this.city = "";
        this.state = "";
        this.country = "";
        this.zipcode = "";
        this.lat = 0.0f;
        this.lng = 0.0f;
    }

    public TripLocation(Address address) {
        this.name = SplurgeHelper.sanitize(address.getFeatureName());
        this.street = SplurgeHelper.sanitize(address.getAddressLine(0));
        this.neighbourhood = SplurgeHelper.sanitize(address.getSubLocality());
        this.city = SplurgeHelper.sanitize(address.getLocality());
        this.state = SplurgeHelper.sanitize(address.getAdminArea());
        this.country = SplurgeHelper.sanitize(address.getCountryName());
        this.zipcode = SplurgeHelper.sanitize(address.getPostalCode());
        this.lat = (float) address.getLatitude();
        this.lng = (float) address.getLongitude();
    }

    public TripLocation(Location location) {
        this.name = "";
        this.street = "";
        this.neighbourhood = "";
        this.city = "";
        this.state = "";
        this.country = "";
        this.zipcode = "";
        this.lat = (float) location.getLatitude();
        this.lng = (float) location.getLongitude();
    }

    public TripLocation(String city, String state, String country) {
        this.name = "";
        this.street = "";
        this.neighbourhood = "";
        this.city = city == null ? "" : city;
        this.state = state == null ? "" : state;
        this.country = country == null ? "" : country;
        this.zipcode = "";
        this.lat = 0;
        this.lng = 0;
    }

    public boolean isGeotagged() {
        return lat != 0.0f || lng != 0.0f;
    }

    public String getLocality() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(neighbourhood)) {
            sb.append(neighbourhood);
            if (!TextUtils.isEmpty(city)) {
                sb.append(", ");
                sb.append(city);
            }
        } else {
            if (!TextUtils.isEmpty(city)) {
                sb.append(city);
            }
        }

        return sb.toString();
    }

    public String getCityCountry() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(city)) {
            sb.append(city);
            if (!TextUtils.isEmpty(country)) {
                sb.append(", ");
                sb.append(country);
            }
        } else {
            if (!TextUtils.isEmpty(country)) {
                sb.append(country);
            }
        }

        return sb.toString();
    }

    public String getStreet() {
        return street;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String printStreet() {
        return TextUtils.isEmpty(street) ? "-" : street;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    public Location toLocation() {
        Location l = new Location(LocationManager.PASSIVE_PROVIDER);
        l.setLatitude(lat);
        l.setLongitude(lng);
        return l;

    }

    protected TripLocation(Parcel in) {
        name = in.readString();
        street = in.readString();
        neighbourhood = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        zipcode = in.readString();
        lat = in.readFloat();
        lng = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(street);
        dest.writeString(neighbourhood);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(zipcode);
        dest.writeFloat(lat);
        dest.writeFloat(lng);
    }

    public static final Parcelable.Creator<TripLocation> CREATOR = new Parcelable.Creator<TripLocation>() {
        public TripLocation createFromParcel(Parcel in) {
            return new TripLocation(in);
        }

        public TripLocation[] newArray(int size) {
            return new TripLocation[size];
        }
    };
}

