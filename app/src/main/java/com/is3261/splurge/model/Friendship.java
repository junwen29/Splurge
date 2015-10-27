package com.is3261.splurge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by junwen29 on 10/18/2015.
 */
public class Friendship {

    @SerializedName("id")
    public final long id;
    @SerializedName("user")
    private User user;
    @SerializedName("friend")
    private User friend;
    @SerializedName("approved")
    private boolean approved;

    public Friendship() {
        id = 0;
    }

    public User getUser() {
        return user;
    }

    public User getFriend() {
        return friend;
    }
}
