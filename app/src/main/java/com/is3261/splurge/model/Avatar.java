package com.is3261.splurge.model;

import android.support.annotation.DrawableRes;

import com.is3261.splurge.R;

import java.util.Random;

/**
 * Created by junwen29 on 11/1/2015.
 * The available avatars with their corresponding drawable resource ids.
 */
public enum Avatar {

    ONE(R.drawable.avatar_1),
    TWO(R.drawable.avatar_2),
    THREE(R.drawable.avatar_3),
    FOUR(R.drawable.avatar_4),
    FIVE(R.drawable.avatar_5),
    SIX(R.drawable.avatar_6),
    SEVEN(R.drawable.avatar_7),
    EIGHT(R.drawable.avatar_8),
    NINE(R.drawable.avatar_9),
    TEN(R.drawable.avatar_10),
    ELEVEN(R.drawable.avatar_11),
    TWELVE(R.drawable.avatar_12),
    THIRTEEN(R.drawable.avatar_13),
    FOURTEEN(R.drawable.avatar_14),
    FIFTEEN(R.drawable.avatar_15),
    SIXTEEN(R.drawable.avatar_16);

    private static final String TAG = "Avatar";

    private final int mResId;

    private static final Avatar[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    Avatar(@DrawableRes final int resId) {
        mResId = resId;
    }

    @DrawableRes
    public int getDrawableId() {
        return mResId;
    }

    public String getNameForAccessibility() {
        return TAG + " " + ordinal() + 1;
    }

    public static Avatar getRandomAvatar()  {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
