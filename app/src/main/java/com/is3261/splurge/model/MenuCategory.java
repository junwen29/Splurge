package com.is3261.splurge.model;

/**
 * Created by junwen29 on 10/8/2015.
 */
public class MenuCategory {
    private final String mName;
    private final String mId;
    private final Theme mTheme;

    public MenuCategory(String mName, String mId, Theme mTheme) {
        this.mName = mName;
        this.mId = mId;
        this.mTheme = mTheme;
    }

    public String getId() {
        return mId;
    }

    public Theme getTheme() {
        return mTheme;
    }

    public String getName() {
        return mName;
    }
}
