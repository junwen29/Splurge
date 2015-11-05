package com.is3261.splurge.model;

import java.util.Date;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class Trip {

    public long id;

    private TripLocation tripLocation;
    private Date date;

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
}
