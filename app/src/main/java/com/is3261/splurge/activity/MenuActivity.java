package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.fragment.MenuSelectionFragment;
import com.is3261.splurge.R;
import com.is3261.splurge.helper.OwnerStore;

//TODO deprecated
public class MenuActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu);
        getLayoutInflater().inflate(R.layout.activity_menu, mContainer, true);
        setUpToolbar();
        setUpDrawerToggle(mToolbar);

        if (savedInstanceState == null) {
            attachMenuGridFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out: {
                signOut();
                return true;
            }
            case R.id.nav:
                startActivity(new Intent(this, NavDrawerActivity.class));
                break;

            case R.id.trip:
                startActivity(new Intent(this,TripActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void signOut(){
//        OwnerStore store = new OwnerStore(this);
//        store.clearAuthToken();
//        store.clear();
//
//        FrontPageActivity.start(this, true, null);
//        finishAffinity();
//    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        ((TextView) toolbar.findViewById(R.id.username)).setText(username);
    }


    private void attachMenuGridFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, MenuSelectionFragment.newInstance())
                .commit();
    }
}
