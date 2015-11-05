package com.is3261.splurge.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.select_location);

        mToolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

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
