package com.is3261.splurge.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class LocationFixer implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    static final String TAG = "LocationFixer";
    public static final int GP_SERVICES_RESOLUTION = 9000;
    public static final int LOCATION_EXPIRY = 5 * 60 * 1000;
    public static final int TIMEOUT = 3 * 1000; // 3 sec

    public static final int ACCURACY_COARSE = 1;
    public static final int ACCURACY_FINE = 2;

    private Context mContext;
    private Location mLastLocation;
    private LocationFixerListener mListener;
    private int mAccuracy;
    private LocationManager mLocationManager;

    // Google Play services Location API
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    // Android framework Location API
    private Criteria mLocationCriteria;
    private android.location.LocationListener mSingleUpdateListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mTimeoutHandler.removeCallbacks(mTimeoutCallback);
            LocationFixer.this.onLocationChanged(location);
            if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            mLocationManager.removeUpdates(mSingleUpdateListener);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    };

    private Runnable mTimeoutCallback = new Runnable() {
        @Override
        public void run() {
            if (mGoogleApiClient != null) {
                if (mGoogleApiClient.isConnected()) {
                    stopLocationUpdates();
                }
            } else {
                if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                mLocationManager.removeUpdates(mSingleUpdateListener);
            }

            if (mListener != null) mListener.onLocationFailed();

            mTimeoutHandler.removeCallbacks(this);
        }
    };
    private Handler mTimeoutHandler = new Handler();

    public LocationFixer(Activity context) {
        this(context, ACCURACY_COARSE);
    }

    public LocationFixer(Activity context, int accuracy) {
        mContext = context;
        mAccuracy = accuracy;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Log.d(TAG, "LocationFixer constructor called!!!!");
        if (isGooglePlayServicesAvailable()) {
            initGooglePlayServicesLocation();
        } else {
            initFrameworkLocation();
            Log.d(TAG, "No google play services available...");
        }

        decideLocationProvider();
    }

    private void initGooglePlayServicesLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.d(TAG, "google api client re-instantiated");

        // TODO
        createLocationRequest();
    }

    private void initFrameworkLocation() {
        mLocationCriteria = new Criteria();
        mLocationCriteria.setCostAllowed(true);
    }

    private void decideLocationProvider() {
        boolean networkOn = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gpsOn = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!networkOn && !gpsOn) {
            if (mListener != null) mListener.onLocationFailed();
            return;
        }

        if (mLocationRequest != null) {
            if (mAccuracy == ACCURACY_COARSE) {
                mLocationRequest.setPriority(networkOn ? LocationRequest.PRIORITY_LOW_POWER : LocationRequest.PRIORITY_HIGH_ACCURACY);
            } else if (mAccuracy == ACCURACY_FINE) {
                mLocationRequest.setPriority(gpsOn ? LocationRequest.PRIORITY_HIGH_ACCURACY : LocationRequest.PRIORITY_LOW_POWER);
            }
        } else {
            mLocationCriteria.setAccuracy(mAccuracy == ACCURACY_COARSE ? Criteria.ACCURACY_COARSE : Criteria.ACCURACY_FINE);
        }
    }

    public void setListener(LocationFixerListener listener) {
        mListener = listener;
    }

    public void start() {
        Log.d(TAG, "Starting...");

        decideLocationProvider();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            String provider = mLocationManager.getBestProvider(mLocationCriteria, true);
            Log.i(TAG, "Provider used: " + provider);
            if (provider != null) {
                if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(provider, 0, 0, mSingleUpdateListener);
            } else {
                if (mListener != null) mListener.onLocationFailed();
            }
        }
    }

    public void stop() {
        Log.d(TAG, "Stopping...");

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
                mGoogleApiClient.disconnect();
            }
        } else {
            if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            mLocationManager.removeUpdates(mSingleUpdateListener);
        }

        mTimeoutHandler.removeCallbacks(mTimeoutCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Handling activity result...");
        if (requestCode != GP_SERVICES_RESOLUTION) {
            // None of our business
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            // TODO
        } else {
            if (mGoogleApiClient == null || mLocationRequest == null)
                initGooglePlayServicesLocation();
            startLocationRequest();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "Failed to connect to Google Play services!");
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult((Activity) mContext, GP_SERVICES_RESOLUTION);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Unresolved error! code: " + result.getErrorCode());
        }

        if (mListener != null) mListener.onLocationFailed();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Connected to Google Play services!");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null && mListener != null) {
            mListener.onLocationChanged(mLastLocation);
        }

        if (isLocationExpired(mLastLocation)) {
            startLocationRequest();
            mTimeoutHandler.postDelayed(mTimeoutCallback, TIMEOUT);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mTimeoutHandler.removeCallbacks(mTimeoutCallback);
        Log.d(TAG, "Location update received: " + location.toString());
        mLastLocation = location;
        if (mListener != null) mListener.onLocationChanged(location);
    }

    public Location getLastKnownLocation() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            // TODO compare network provider and gps provider
            if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
            return mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    public void requestLocation(LocationFixerListener listener) {
        mListener = listener;
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationRequest();
            mTimeoutHandler.postDelayed(mTimeoutCallback, TIMEOUT);
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (resultCode == ConnectionResult.SUCCESS) {
            return true;
        } else {
            Log.w(TAG, "Google Play services unavailable! code: " + resultCode);
            return false;
        }
    }

    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0)
                .setFastestInterval(0)
                .setNumUpdates(1);
    }

    protected void startLocationRequest(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, this);
    }

    protected void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    private static boolean isLocationExpired(Location location) {
        if (location == null) return true;

        long timeDelta = System.currentTimeMillis() - location.getTime();
        return timeDelta > LOCATION_EXPIRY;
    }

    public static interface LocationFixerListener {
        public void onLocationChanged(Location location);
        public void onLocationFailed();
    }

    public static interface LocationFixerProvider {
        public LocationFixer getLocationFixer();
    }
}
