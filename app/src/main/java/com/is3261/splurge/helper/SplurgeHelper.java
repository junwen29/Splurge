package com.is3261.splurge.helper;

import android.content.res.Resources;
import android.location.Location;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by junwen29 on 11/1/2015.
 */
public class SplurgeHelper {

    public static int convertDipToPx(int dp, Resources res) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static String printDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        if (date == null) date = new Date();
        return formatter.format(date);
    }

    public static boolean isValidLocation(Location location) {
        if (location == null) return false;
        if (location.getLatitude() == 0 && location.getLongitude() == 0) return false;

        return true;
    }
}
