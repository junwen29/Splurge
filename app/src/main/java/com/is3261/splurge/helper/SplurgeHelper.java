package com.is3261.splurge.helper;

import android.content.res.Resources;
import android.location.Location;
import android.util.TypedValue;

import java.math.BigDecimal;
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
        return location != null && !(location.getLatitude() == 0 && location.getLongitude() == 0);

    }

    public static String sanitize(String string) {
        return sanitize(string, "");
    }

    public static String sanitize(String string, String replacement) {
        if (string == null) return replacement;
        return string;
    }

    public static String locationToString(Location location) {
        if (location == null) return null;

        return String.format(Locale.US, "%f,%f", location.getLatitude(), location.getLongitude());
    }

    public static Float round(Float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
