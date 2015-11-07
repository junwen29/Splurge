package com.is3261.splurge;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.is3261.splurge.activity.FrontPageActivity;

/**
 * Created by home on 7/11/15.
 */
public class WebAppInterface {
    Activity activity;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Activity activity) {
        this.activity = activity;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void getStarted(){
//        Intent i = new Intent(activity, FrontPageActivity.class);
//        activity.startActivity(i);
        FrontPageActivity.start(activity, false, null);
    }
}