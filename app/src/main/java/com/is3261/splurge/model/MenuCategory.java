package com.is3261.splurge.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by junwen29 on 10/8/2015.
 */
public class MenuCategory implements Parcelable{

    public static final String TAG = "MenuCategory";

    private final String mName;
    private final String mId;
    private final Theme mTheme;

    public MenuCategory(String mName, String mId, Theme mTheme) {
        this.mName = mName;
        this.mId = mId;
        this.mTheme = mTheme;
    }

    protected MenuCategory(Parcel in) {
        mName = in.readString();
        mId = in.readString();
        mTheme = Theme.values()[in.readInt()];
    }

    public static final Creator<MenuCategory> CREATOR = new Creator<MenuCategory>() {
        @Override
        public MenuCategory createFromParcel(Parcel in) {
            return new MenuCategory(in);
        }

        @Override
        public MenuCategory[] newArray(int size) {
            return new MenuCategory[size];
        }
    };

    public String getId() {
        return mId;
    }

    public Theme getTheme() {
        return mTheme;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mId);
        dest.writeInt(mTheme.ordinal());
    }
}
