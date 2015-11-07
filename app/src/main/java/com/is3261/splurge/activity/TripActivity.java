package com.is3261.splurge.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.adapter.TripAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.async_task.LoadTripsTask;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Trip;
import com.is3261.splurge.view.MarkerOptionsFactory;
import com.is3261.splurge.view.TripCard;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TripActivity extends NavDrawerActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private View mProgressView;
    private RecyclerView mRecyclerView;
    private RelativeLayout mMapView;
    private ViewPager mMapVenuePager;

    private TripAdapter mAdapter;
    private MapPagerAdapter mMapPagerAdapter;
    private LoadTripsTask mTask = null;
    private String mUserId;
    private boolean mMapVisible = false;
    private MenuItem mToggleMenuItem;

    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private HashMap<Marker, Trip> mMarkerHash;
    private Marker mSelectedMarker;
    private ArrayList<Marker> mMarkerList;
    private BitmapDescriptor mMapDotBitmap;
    private BitmapDescriptor mMapPinBitmap;

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
        initMap();
        setUpDrawerToggle(mToolbar);

        mAdapter = new TripAdapter(new ArrayList<Trip>(),this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        initMapVenuePager();

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
        mToggleMenuItem.setEnabled(false);
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
        mMapView = (RelativeLayout) findViewById(R.id.map_view);
        mMapVenuePager = (ViewPager) findViewById(R.id.map_venue_pager);
    }

    private void initMapVenuePager() {
        mMarkerList = new ArrayList<>();
        mSelectedMarker = null;
        mMapPagerAdapter = new MapPagerAdapter(getSupportFragmentManager());
        mMapVenuePager.setAdapter(mMapPagerAdapter);
        mMapVenuePager.setPageMargin(SplurgeHelper.convertDipToPx(8, getResources()));
        mMapVenuePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    int idx = mMapVenuePager.getCurrentItem();
                    if (mMarkerList.size() > idx) {
                        Marker m = mMarkerList.get(idx);
                        setSelectedMarker(m);
                    } else {
                        setSelectedMarker(null);
                    }
                }
            }
        });
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

                // enable the toggle button after successful load
                mToggleMenuItem.setVisible(true);
                mToggleMenuItem.setEnabled(true);

                //update map fragment
                for (Marker m : mMarkerList) {
                    m.remove();
                }
                mMarkerList.clear();
                if (mMarkerHash != null)
                    mMarkerHash.clear();

                mMapPagerAdapter.notifyDataSetChanged();
                setSelectedMarker(null);
                updateMarkers(trips);
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

            if (mMapVisible){
                mMapView.setVisibility(show ? View.GONE : View.VISIBLE);
                mMapView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mMapView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            } else {
                mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }

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
            if (mMapVisible){
                mMapView.setVisibility(show ? View.GONE : View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }
    }

    private void showMap() {
        if (mMapFragment == null || mMap == null){
            initMap();
            Toast.makeText(this,"Map is not ready", Toast.LENGTH_SHORT).show();
            return;
        }

        mRecyclerView.setVisibility(View.GONE);
        mMapView.setVisibility(View.VISIBLE);

        mMapVisible = true;
        mToggleMenuItem.setIcon(R.drawable.ic_view_list_white_36dp);
    }

    private void hideMap() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mMapView.setVisibility(View.INVISIBLE);

        mMapVisible = false;
        mToggleMenuItem.setIcon(R.drawable.ic_map_white_36dp);
    }

    private void initMap() {
        // setup map_current_location if needed
        // add map_current_location fragment
        if (mMapFragment == null) {
            mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.setOnMarkerClickListener(this);

        mMap = googleMap;
        mMapDotBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_drop_black_48dp);
        mMapPinBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_48dp);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void updateMarkers(Collection<Trip> trips) {

        if (mMap == null) initMap();

        // Add venues markers if exist
        if (mMarkerHash == null) mMarkerHash = new HashMap<>(trips.size());

        if (mMap != null && trips.size() > 0) {
            LatLngBounds.Builder llb = new LatLngBounds.Builder();
            for (Trip t : trips) {
                Marker m = mMap.addMarker(MarkerOptionsFactory.trip(t, this).anchor(0.5f, 0.5f));
                mMarkerList.add(m);
                mMarkerHash.put(m, t);
                llb.include(t.getLatLng());
            }
            if (mSelectedMarker == null)
                setSelectedMarker(mMarkerList.get(0));

            mMap.setOnMarkerClickListener(this);

            try {
                final LatLngBounds latLngBounds = llb.build();
                if (mMapFragment != null && mMapFragment.getView() != null) {
                    mMapFragment.getView().post(new Runnable() {
                        @Override
                        public void run() {
                            int horizontalPadding = SplurgeHelper.convertDipToPx(50, getResources());
                            int verticalPadding = SplurgeHelper.convertDipToPx(150, getResources());
                            mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngBounds(latLngBounds, mMapView.getWidth() - horizontalPadding, mMapView.getHeight() - mMapVenuePager.getHeight() - verticalPadding, 0)
                            );
                        }
                    });
                }
            } catch (IllegalStateException e) {
                // do nothing
            }
        }
    }

    private void setSelectedMarker(Marker m) {
        if (m == null) {
            mSelectedMarker = null;
            return;
        }
        if (mSelectedMarker != null) {
            mSelectedMarker.setAnchor(0.5f, 0.5f);
            mSelectedMarker.setIcon(mMapDotBitmap);
        }
        mSelectedMarker = m;
        mSelectedMarker.setAnchor(0.5f, 0.91f);
        mSelectedMarker.setIcon(mMapPinBitmap);

        Projection projection = mMap.getProjection();
        Point screenPosition = projection.toScreenLocation(mSelectedMarker.getPosition());
        int padding = 100;
        Rect rect = new Rect(mMapView.getLeft() + padding, mMapView.getTop() + padding,
                mMapView.getRight() - padding, mMapView.getBottom() - (padding + mMapVenuePager.getHeight()));
        if (!rect.contains(screenPosition.x, screenPosition.y)) {
            updateMapCamera(CameraUpdateFactory.newLatLng(m.getPosition()), 800);
        }
    }

    private void updateMapCamera(CameraUpdate update, int duration) {
        if (mMap != null) {
            if (duration == 0) {
                mMap.moveCamera(update);
            } else {
                mMap.animateCamera(update, duration, null);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (mMapPagerAdapter != null) {
            mMapPagerAdapter.notifyDataSetChanged();
        }
    }

    private class MapPagerAdapter extends FragmentStatePagerAdapter {

        public MapPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mAdapter.getItemCount();
        }

        @Override
        public Fragment getItem(int position) {
            return MapVenueFragment.newInstance(mAdapter.getItem(position));
        }
    }

    public static class MapVenueFragment extends BaseFragment {

        Trip mTrip;

        static MapVenueFragment newInstance(Trip t) {
            MapVenueFragment f = new MapVenueFragment();

            f.setTrip(t);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_map_trip, container, false);
            TripCard tripCard = (TripCard) v.findViewById(R.id.card_trip);

            if (mTrip != null) {
                tripCard.update(mTrip);
                tripCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent i = new Intent(getActivity(), VenueActivity.class).putExtra("venue", mVenue);
//                        getActivity().startActivity(i);
                    }
                });
            }
            return v;
        }

        private void setTrip(Trip t) {
            mTrip = t;
        }
    }
}
