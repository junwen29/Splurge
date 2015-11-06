package com.is3261.splurge.helper;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.util.TypedValue;

import com.is3261.splurge.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by junwen29 on 11/1/2015.
 */
public class SplurgeHelper {
    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    public static final long WEEK_MILLIS = 7 * DAY_MILLIS;
    public static final long YEAR_MILLIS = 52 * WEEK_MILLIS;

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

    public static String getTimeAgo(Context context, long time, long now) {
        if (time > now || time <= 0) {
            return context.getString(R.string.ago_now);
        }

        Resources r = context.getResources();
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            int count = (int) (diff / SECOND_MILLIS);
            return r.getQuantityString(R.plurals.seconds_ago, count, count);
        } else if(diff < HOUR_MILLIS){
            int count = (int) (diff / MINUTE_MILLIS);
            return r.getQuantityString(R.plurals.minutes_ago, count, count);
        }else if (diff < DAY_MILLIS) {
            int count = (int) (diff / HOUR_MILLIS);
            return r.getQuantityString(R.plurals.hours_ago, count, count);
        }else if (diff < WEEK_MILLIS) {
            int count = (int) (diff / DAY_MILLIS);
            return r.getQuantityString(R.plurals.days_ago, count, count);
        } else if (diff < 8 * DAY_MILLIS) {
            int count = (int) (diff / WEEK_MILLIS);
            return r.getQuantityString(R.plurals.weeks_ago, count, count);
        } else if (diff < YEAR_MILLIS){
            return printDate(new Date(time),"MMM d 'at' h:mmaa");
        }else {
            return printDate(new Date(time),"MMM d ',' yyyy");
        }
    }
}
