package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.is3261.splurge.R;
import com.is3261.splurge.helper.OwnerStore;

public class MainActivity extends AppCompatActivity {

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
            startActivity(new Intent (this, FrontPageActivity.class));
        }
        //TODO create splash screen with just app logo
        setContentView(R.layout.activity_main);
        setup();

    }

    private void goToMain() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
