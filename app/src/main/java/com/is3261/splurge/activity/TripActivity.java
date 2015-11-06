package com.is3261.splurge.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.adapter.TripAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.async_task.LoadTripsTask;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.Trip;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.Collection;

public class TripActivity extends NavDrawerActivity {

    private View mProgressView;
    private RecyclerView mRecyclerView;

    private TripAdapter mAdapter;
    private LoadTripsTask mTask = null;
    private String mUserId;
    private boolean mMapVisible = false;
    private MenuItem mToggleMenuItem;

    private static final String TAG = "TripActivity";

    @Override
    public void updateActiveDrawerItem() {
        //set selected menu
        mNavigationView.getMenu().findItem(mSelectedDrawerItemId).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set drawer id
        mSelectedDrawerItemId = R.id.nav_trips;

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_trip, mContainer, true);

//        setContentView(R.layout.activity_trip);
        init();
        setUpDrawerToggle(mToolbar);

        mAdapter = new TripAdapter(new ArrayList<Trip>(),this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        OwnerStore ownerStore = new OwnerStore(this);
        mUserId = ownerStore.getOwnerId();

        loadTrips();

        FloatingActionButton fab = new FloatingActionButton.Builder(this).setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_addbtn)).build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        ImageView itemIcon = new ImageView(this);
        SubActionButton button1 = itemBuilder.build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TripActivity.this, CreateTripActivity.class));
            }
        });

        ImageView itemIcon2 = new ImageView(this);

        SubActionButton button2 = itemBuilder.build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mealSpilt_i = new Intent(TripActivity.this, SpiltMealActivity.class);
                startActivity(mealSpilt_i);
            }
        });

//        ImageView itemIcon3 = new ImageView(this);
        SubActionButton button3 = itemBuilder.build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3).attachTo(fab).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip, menu);

        mToggleMenuItem = menu.findItem(R.id.toggle);
        // hide menu item until results loaded
        mToggleMenuItem.setVisible(false);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mMapVisible) {
            hideMap();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.refresh:
                loadTrips();
                return true;
            case R.id.toggle:
                if (mMapVisible) {
                    hideMap();
                } else {
                    showMap();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void loadTrips(){
        CollectionListener<Trip> mListener = new CollectionListener<Trip>() {
            @Override
            public void onResponse(Collection<Trip> trips) {
                showRefreshing(false);
                mAdapter.clear();
                mAdapter.addAll(trips);
                mAdapter.notifyDataSetChanged();
                mTask = null;
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showRefreshing(false);
                Log.d(TAG, "Error: " /*+ volleyError.toString()*/);
                mTask = null;
            }
        };

        if (mTask == null){
            showRefreshing(true);

            mTask = new LoadTripsTask(this, mListener, mUserId);
            mTask.execute((Void) null);
        }
    }

    private void showRefreshing(final boolean show){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showMap() {
//        if (mMap == null) initMap();

        mRecyclerView.setVisibility(View.GONE);
//        mContainerMapLayout.setVisibility(View.VISIBLE);

        mMapVisible = true;
        mToggleMenuItem.setIcon(R.drawable.ic_view_list_white_36dp);
    }

    private void hideMap() {
        mRecyclerView.setVisibility(View.VISIBLE);
//        mContainerMapLayout.setVisibility(View.INVISIBLE);

        mMapVisible = false;
        mToggleMenuItem.setIcon(R.drawable.ic_map_white_36dp);
    }

}
