package com.is3261.splurge.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.is3261.splurge.Constant;

/**
 * Created by junwen29 on 10/7/2015.
 */
public class SplurgeGson {

    private static Gson sInstance;

    public static Gson getInstance() {
        if (sInstance == null) {
            sInstance = new GsonBuilder()
                    .setDateFormat(Constant.GENERIC_DATE_FORMAT)
                    .create();
        }
        return sInstance;
    }
}
