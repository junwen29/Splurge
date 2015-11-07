package com.is3261.splurge.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.is3261.splurge.R;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.service.UpdateServerService;

import java.io.IOException;

public class FrontPageActivity extends AppCompatActivity implements View.OnClickListener {

    private View mContentView;
    private static final int REQ_SIGNUP = 291;
    private static final int REQ_LOGIN = 292;

    // GCM
    private GoogleCloudMessaging mGCM;
    private String mRegId;
    private static final String GCM_SENDER_ID = "829855570993"; //Google API Project Number

    public static void start(Activity activity, boolean clear, ActivityOptions options) {
        Intent starter = new Intent(activity, FrontPageActivity.class);
        if (clear)
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (options == null) {
            activity.startActivity(starter);
            activity.overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        } else {
            activity.startActivity(starter, options.toBundle());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_front_page);
        setupViews();
    }

    private void setupViews(){
        mContentView = findViewById(R.id.fullscreen_content);
        Button signupButton = (Button) findViewById(R.id.signup_button);
        Button loginButton = (Button) findViewById(R.id.login_button);

        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
    }

    @SuppressLint("InlinedApi")
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                login();
                break;
            case R.id.signup_button:
                signup();
                break;
            default:
                break;
        }
    }

    private void signup() {
        SignupActivity.startForResult(this, REQ_SIGNUP, null);
    }

    private void login() {
//        startActivityForResult(new Intent(this,LoginActivity.class),REQ_LOGIN);
        LoginActivity.startForResult(this, REQ_LOGIN, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQ_SIGNUP:
                case REQ_LOGIN:
                    startActivity(new Intent(this, TripActivity.class));
                    finish();
                    break;
                default:
                    //do nothing
            }
            //register GCM no matter login or signup
            registerGCM();
        }
    }

    private void registerGCM(){
        mGCM = GoogleCloudMessaging.getInstance(this);
        mRegId = getGCMRegId();
        if (mRegId.isEmpty()){
            registerGCMInBackground();
        }
    }

    private void registerGCMInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                Context context = getApplicationContext();
                try{
                    if (mGCM == null){
                        mGCM = GoogleCloudMessaging.getInstance(context);
                    }
                    mRegId = mGCM.register(GCM_SENDER_ID);
                    msg = "Device registered, registration ID= "+ mRegId;

                    startService(UpdateServerService.deviceToken(context, true, mRegId));
                    // Persist the regID - no need to register again.
                    OwnerStore store = new OwnerStore(context);
                    store.storeRegistrationId(context, mRegId);

                    Log.i("GCM", msg);
                } catch (IOException e) {
                    msg = "Error: " + e.getMessage();
                    e.printStackTrace();
                }

                return msg;
            }
        }.execute(null, null, null);
    }

    private String getGCMRegId(){
        OwnerStore store = new OwnerStore(getApplicationContext());
        String registrationId = store.getGCMRegistrationId();
        if (registrationId.isEmpty()){
            Log.d("getGCMRegID", "GCM Registration ID not found.");
            return "";
        }
        return registrationId;
    }

}
