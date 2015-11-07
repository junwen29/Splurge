package com.is3261.splurge;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.is3261.splurge.activity.TripActivity;

/**
 * Created by home on 7/11/15.
 */
public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void getStarted(){
        Intent i = new Intent(mContext, TripActivity.class);
        mContext.startActivity(i);
    }
}