package com.is3261.splurge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by junwen29 on 10/7/2015.
 */
public class User implements Parcelable {
    @SerializedName("id")
    public final long id;

    @SerializedName("username")
    protected String username;

    @SerializedName("email")
    protected String email;

    public User() {
        this.id = 0;
    }

    public User(long id) {
        this.id = id;
    }

    protected User(Parcel in) {
        id = in.readLong();
        username = in.readString();
        email = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(email);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
