package com.is3261.splurge.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.helper.AlertDialogFactory;
import com.is3261.splurge.helper.LocationFixer;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.TripLocation;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class CreateTripActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQ_SELECT_LOCATION = 3001;

    private Toolbar mToolbar;
    private Button mDateButton;
    private Button mLocationButton;

    private Location mTripLocation;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        init();

        //initialize location
        mTripLocation = new Location(LocationManager.PASSIVE_PROVIDER);
        mTripLocation.setLatitude(getIntent().getFloatExtra("lat", 0));
        mTripLocation.setLongitude(getIntent().getFloatExtra("lon", 0));

        if (!SplurgeHelper.isValidLocation(mTripLocation)) {
            mTripLocation = new LocationFixer(this).getLastKnownLocation();
        }
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

    @Override
    public void onBackPressed() {
        if (mAlertDialog == null) {
            mAlertDialog = AlertDialogFactory.unsaved(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateTripActivity.super.onBackPressed();
                }
            });
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDateButton = (Button) findViewById(R.id.button_date);
        mLocationButton = (Button) findViewById(R.id.button_location);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setTitle("Create Trip");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDateButton.setOnClickListener(this);
        mLocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_date:
                break;
            case R.id.button_location:
                Intent i = new Intent(this, SelectLocationActivity.class);
                i.putExtra("location", mTripLocation);
                startActivityForResult(i, REQ_SELECT_LOCATION);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == REQ_SELECT_LOCATION) {
            TripLocation location = data.getParcelableExtra("location");
            if (location != null) {
                mLocationButton.setText(location.name);
            }
        }
    }
}
