package com.is3261.splurge.helper;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by junwen29 on 11/1/2015.
 */
public class SplurgeHelper {

    public static int convertDipToPx(int dp, Resources res) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }
}
