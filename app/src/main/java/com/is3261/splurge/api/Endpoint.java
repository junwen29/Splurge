package com.is3261.splurge.api;

import com.is3261.splurge.BuildConfig;

/**
 * Created by junwen29 on 10/7/2015.
 */
public class Endpoint {

    public static final String SERVER_URL = BuildConfig.BUILD_TYPE.equals("debug") ? "http://192.168.0.105:3000/" : "https://splurge-rails.herokuapp.com/";

    // Device
//    public static final String REGISTER_DEVICE = P1_SERVER_URL + "devices";
//    public static final String UNREGISTER_DEVICE = P1_SERVER_URL + "devices?auth_token=%s&device_token=%s&device_type=android";

    //registration
    public static final String SIGNUP = SERVER_URL + "accounts/sign_up";

    //owner session
    public static final String LOGIN = SERVER_URL + "accounts/sign_in";
    public static final String LOGOUT = SERVER_URL + "accounts/sign_out";

    // friendship

    public static final String ADD_FRIEND = SERVER_URL + "friendship/create?auth_token=%s&user_id=%s&email=%s";
}
