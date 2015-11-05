package com.is3261.splurge.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.Foursquare;
import com.is3261.splurge.fragment.SelectLocationFragment;
import com.is3261.splurge.helper.LocationFixer;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.TripLocation;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class SelectLocationActivity extends BaseActivity implements SelectLocationFragment.LocationLoader{
    private final static String FRAGMENT_TAG = "SELECT_LOCATION_FRAGMENT";

    private Toolbar mToolbar;

    private LocationFixer mLocationFixer;
    private Fragment mFragment;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        init();

        mLocation = getIntent().getParcelableExtra("location");

        if(mFragment == null){
            mFragment = SelectLocationFragment.newInstance(mLocation);
            getSupportFragmentManager().beginTransaction().add(R.id.content, mFragment).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
        }

        mLocationFixer = new LocationFixer(this);
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.select_location);

//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar!= null){
//            actionBar.setTitle("Select Location");
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.select_location, menu);
//
//        return true;
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationFixer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationFixer.stop();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment state
        getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, mFragment);
    }

    @Override
    public void load(final String query, final CollectionListener<TripLocation> listener) {
        if (mLocation == null) {
            mLocation = mLocationFixer.getLastKnownLocation();
        }

        if (mLocation == null) {
            mLocationFixer.requestLocation(new LocationFixer.LocationFixerListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mLocation = location;
                    Foursquare foursquare = new Foursquare(SelectLocationActivity.this);
                    foursquare.fetch(query, SplurgeHelper.locationToString(location), listener);
                }

                @Override
                public void onLocationFailed() {
                    Foursquare foursquare = new Foursquare(SelectLocationActivity.this);
                    foursquare.fetch(query, null, listener);
                }
            });
        } else {
            Foursquare foursquare = new Foursquare(SelectLocationActivity.this);
            foursquare.fetch(query, SplurgeHelper.locationToString(mLocation), listener);
        }
    }

    @Override
    public SearchView getSearchView() {
        return (SearchView) mToolbar.getMenu().findItem(R.id.search).getActionView();
    }
}
