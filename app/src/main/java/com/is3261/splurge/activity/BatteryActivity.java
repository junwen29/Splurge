package com.is3261.splurge.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.fragment.SplitMealFragmentThree;

/**
 * Created by junwen29 on 11/7/2015.
 */
public class BatteryActivity extends NavDrawerActivity {

    private TextView batteryLevel, batteryVoltage, batteryTemperature, batteryTechnology, batteryStatus, batteryHealth;

    private BatteryReceiver batteryReceiver;
    private IntentFilter batteryIntentFilter;
    private Toolbar mToolbar;

    @Override
    public void updateActiveDrawerItem() {
        mNavigationView.getMenu().findItem(mSelectedDrawerItemId).setChecked(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set drawer id
        mSelectedDrawerItemId = R.id.nav_battery;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_battery, mContainer, true);

        init();

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Battery Status");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        batteryReceiver = new BatteryReceiver();
        batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        batteryLevel = (TextView) findViewById(R.id.batterylevel);
        batteryVoltage = (TextView) findViewById(R.id.batteryvoltage);
        batteryTemperature = (TextView) findViewById(R.id.batterytemperature);
        batteryTechnology = (TextView) findViewById(R.id.batterytechology);
        batteryStatus = (TextView) findViewById(R.id.batterystatus);
        batteryHealth = (TextView) findViewById(R.id.batteryhealth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(batteryReceiver, batteryIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);
    }

    class BatteryReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

                batteryLevel.setText("Level: " + String.valueOf(intent.getIntExtra("level", 0)) + "%");
                batteryVoltage.setText("Voltage: " + String.valueOf((float) intent.getIntExtra("voltage", 0) / 1000) + "V");
                batteryTemperature.setText("Temperature: " + String.valueOf((float) intent.getIntExtra("temperature", 0) / 10) + "c");
                batteryTechnology.setText("Technology: " + intent.getStringExtra("technology"));

                int status = intent.getIntExtra("status",BatteryManager.BATTERY_STATUS_UNKNOWN);

                String strStatus;
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    strStatus = "Charging";
                } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    strStatus = "Discharging";
                } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                    strStatus = "Not charging";
                } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                    strStatus = "Full";
                } else {
                    strStatus = "Unknown";
                }
                batteryStatus.setText("Status: " + strStatus);

                int health = intent.getIntExtra("health",BatteryManager.BATTERY_HEALTH_UNKNOWN);
                String strHealth;
                if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                    strHealth = "Good";
                } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                    strHealth = "Over Heat";
                } else if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
                    strHealth = "Dead";
                } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                    strHealth = "Over Voltage";
                } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                    strHealth = "Unspecified Failure";
                } else {
                    strHealth = "Unknown";
                }

                batteryHealth.setText("Health: " + strHealth);
            }
        }
    }
}
