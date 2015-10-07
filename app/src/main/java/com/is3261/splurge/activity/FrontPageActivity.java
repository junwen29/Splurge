package com.is3261.splurge.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.is3261.splurge.R;
import com.is3261.splurge.helper.OwnerStore;

public class FrontPageActivity extends AppCompatActivity implements View.OnClickListener {

    private View mContentView;
    private static final int REQ_SIGNUP = 291;
    private static final int REQ_LOGIN = 292;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_front_page);
        setupViews();
        hide();
    }

    private void setupViews(){
        mContentView = findViewById(R.id.fullscreen_content);
        Button signupButton = (Button) findViewById(R.id.signup_button);
        Button loginButton = (Button) findViewById(R.id.login_button);

        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
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
        startActivityForResult(new Intent(this,SignupActivity.class),REQ_SIGNUP);
    }

    private void login() {
        startActivityForResult(new Intent(this,LoginActivity.class),REQ_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQ_SIGNUP:
                case REQ_LOGIN:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
                default:
                    //do nothing
            }
            //TODO register GCM no matter login or signup
        }
    }
}
