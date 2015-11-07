package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.is3261.splurge.R;
import com.is3261.splurge.helper.OwnerStore;

public class SplashScreenActivity extends AppCompatActivity {

    public enum SessionState {
        LOGGED_OUT,
        LOGGED_IN,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionState sessionState = getSessionState();

        if (sessionState == SessionState.LOGGED_IN){
            goToMain();
        }
        else {
            startActivity(new Intent(this, WebViewActivity.class));
        }
        //TODO create splash screen with just app logo
        setContentView(R.layout.activity_splash_screen);
        setup();
        finishAfterTransition();
    }

    private void goToMain() {
        startActivity(new Intent(this, TripActivity.class));
    }

    private void setup(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private SessionState getSessionState() {
        OwnerStore ownerStore = new OwnerStore(this);
        if (ownerStore.getAuthToken() == null) {
            return SessionState.LOGGED_OUT;
        }
        else {
            return SessionState.LOGGED_IN;
        }
    }

}
