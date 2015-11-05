package com.is3261.splurge.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.is3261.splurge.Constant;
import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.dialog.TripDateSelectDialog;
import com.is3261.splurge.helper.AlertDialogFactory;
import com.is3261.splurge.helper.LocationFixer;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Trip;
import com.is3261.splurge.model.TripLocation;

import java.util.Date;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class CreateTripActivity extends BaseActivity implements View.OnClickListener, TripDateSelectDialog.TripDateSelectorListener {

    public static final int REQ_SELECT_LOCATION = 3001;

    private LinearLayout mContainer;
    private Toolbar mToolbar;
    private Button mDateButton;
    private Button mLocationButton;
    private Button mDoneButton;
    private EditText mTitle;

    private Location mTripLocation;
    private AlertDialog mAlertDialog;

    private Trip mTrip;

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

        mTrip = new Trip(0);
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
        mTitle = (EditText) findViewById(R.id.title);
        mContainer = (LinearLayout) findViewById(R.id.container);
        mDoneButton = (Button) findViewById(R.id.button_done);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setTitle("Create Trip");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDateButton.setOnClickListener(this);
        mLocationButton.setOnClickListener(this);
        mDoneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_date:
                TripDateSelectDialog dialog = TripDateSelectDialog.newInstance();
                dialog.setListener(this);
                dialog.show(getSupportFragmentManager(), "date");
                break;
            case R.id.button_location:
                Intent i = new Intent(this, SelectLocationActivity.class);
                i.putExtra("location", mTripLocation);
                startActivityForResult(i, REQ_SELECT_LOCATION);
                break;
            case R.id.button_done:
                attemptSubmit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

//        mTrip = data.getParcelableExtra("trip");

        if (requestCode == REQ_SELECT_LOCATION) {
            TripLocation location = data.getParcelableExtra("location");

            if (location != null) {
                mLocationButton.setText(location.name);
                mTrip.setTripLocation(location);
            }
        }
    }

    @Override
    public void onFinish(Date date) {
        mTrip.setDate(date);
        String dateString = SplurgeHelper.printDate(date, Constant.TRIP_DATE_FORMAT);
        mDateButton.setText(dateString);
    }

    private void attemptSubmit(){
        boolean cancel = false;
        View focusView = null;
        String message = "";

        //reset error
        mTitle.setError(null);

        String title = mTitle.getEditableText().toString();
        TripLocation location = mTrip.getTripLocation();
        Date date = mTrip.getDate();

        if (TextUtils.isEmpty(title)){
            focusView = mTitle;
            cancel = true;
            mTitle.setError(getString(R.string.error_field_required));
        }

        else if (location == null){
            focusView = mLocationButton;
            cancel = true;
            message = "Please choose a location.";
        }

        else if (date == null){
            focusView = mDateButton;
            cancel = true;
            message = "Please choose a date.";
        }

        if (cancel){
            focusView.requestFocus();
            if (!TextUtils.isEmpty(message)){
                Snackbar.make(mContainer, message, Snackbar.LENGTH_LONG).show();
            }
        } else {
            submit();
        }
    }

    private void submit(){

    }
}
