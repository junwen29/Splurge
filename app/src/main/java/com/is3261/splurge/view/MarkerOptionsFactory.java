package com.is3261.splurge.view;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.is3261.splurge.Constant;
import com.is3261.splurge.R;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Trip;

/**
 * Created by junwen29 on 11/7/2015.
 */
public class MarkerOptionsFactory {

    public static MarkerOptions trip(Trip t, Context context) {
        return tripSimple(t, BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_drop_black_48dp))
                .title(t.getTitle())
                .snippet(SplurgeHelper.printDate(t.getDate(), Constant.TRIP_DATE_FORMAT));
    }

    public static MarkerOptions tripSimple(Trip t, BitmapDescriptor icon) {
        return new MarkerOptions()
                .position(t.getLatLng())
                .icon(icon);
    }

}
