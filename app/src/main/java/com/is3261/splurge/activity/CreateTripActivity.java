package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class CreateTripActivity extends BaseActivity {

    public static final int REQ_SELECT_PLACE = 3001;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setTitle("Create Trip");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
