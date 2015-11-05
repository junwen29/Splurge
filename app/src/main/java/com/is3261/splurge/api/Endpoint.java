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
    public static final String APPROVE_FRIEND = SERVER_URL + "friendship/approve?auth_token=%s&user_id=%s&friend_id=%s";
    public static final String REJECT_FRIEND = SERVER_URL + "friendship/reject?auth_token=%s&user_id=%s&friend_id=%s";
    public static final String FRIENDS_REQUESTED = SERVER_URL + "friendship/pending?auth_token=%s&user_id=%s";
    public static final String FRIENDS_FOR_APPROVAL = SERVER_URL + "friendship/requests?auth_token=%s&user_id=%s";
    public static final String ALL_FRIENDS = SERVER_URL + "friendship/friends?auth_token=%s&user_id=%s";

    //expenses
    public static final String LENDS = SERVER_URL + "expenses/lends?auth_token=%s&user_id=%s";
    public static final String ALL_LENDS = SERVER_URL + "expenses/all_lends?auth_token=%s&user_id=%s";
    public static final String DEBTS = SERVER_URL + "expenses/debts?auth_token=%s&user_id=%s";
    public static final String ALL_DEBTS = SERVER_URL + "expenses/all_debts?auth_token=%s&user_id=%s";
    public static final String CREATE_EXPENSE = SERVER_URL + "expenses/create?auth_token=%s&amount=%s&currency=%s&spender_id=%s&borrower_id=%s";
    public static final String SETTLE_UP = SERVER_URL + "expenses/settle?auth_token=%s&expense_id=%s";
}
