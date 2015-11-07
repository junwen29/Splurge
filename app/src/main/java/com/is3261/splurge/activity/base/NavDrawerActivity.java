package com.is3261.splurge.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.AboutUsActivity;
import com.is3261.splurge.activity.AddFriendActivity;
import com.is3261.splurge.activity.DebtActivity;
import com.is3261.splurge.activity.FrontPageActivity;
import com.is3261.splurge.activity.LoanActivity;
import com.is3261.splurge.activity.ProfileActivity;
import com.is3261.splurge.activity.TripActivity;
import com.is3261.splurge.helper.OwnerStore;

public abstract class NavDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView mNavigationView;
    protected FrameLayout mContainer;
    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    protected TextView mTitle;
    protected TextView mSubtitle;
    protected int mSelectedDrawerItemId = -1;

    public abstract void updateActiveDrawerItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mContainer = (FrameLayout) findViewById(R.id.content);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Show owner's username and email address
        mTitle = (TextView) findViewById(R.id.title);
        mSubtitle = (TextView) findViewById(R.id.subtitle);
        OwnerStore ownerStore = new OwnerStore(this);
        String username = ownerStore.getUsername();
        String email = ownerStore.getEmail();
        mTitle.setText(username);
        mSubtitle.setText(email);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    public void setUpDrawerToggle(Toolbar toolbar){
        mToolbar = toolbar;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateActiveDrawerItem();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == mSelectedDrawerItemId)
            return false;

        switch (id){
            case R.id.nav_trips:
                startActivity(new Intent(this, TripActivity.class));
                finishAffinity();//clear all previous activities
                break;

            case R.id.nav_debts:
                startActivity(new Intent(this, DebtActivity.class));
                finishAffinity();//clear all previous activities
                break;

            case R.id.nav_loans:
                startActivity(new Intent(this, LoanActivity.class));
                finishAffinity();//clear all previous activities
                break;

            case R.id.nav_settings:
                startActivity(new Intent(this, AboutUsActivity.class));
                finishAffinity();//clear all previous activities
                break;

            case R.id.nav_my_friends:
                startActivity(new Intent(this, ProfileActivity.class));
                finishAffinity();//clear all previous activities
                break;

            case R.id.nav_add_friend:
                startActivity(new Intent(this, AddFriendActivity.class));
                finishAffinity();//clear all previous activities
                break;

            case R.id.nav_logout:
                signOut();
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        mSelectedDrawerItemId = id;
        return true;
    }

    protected void signOut(){
        OwnerStore store = new OwnerStore(this);
        store.clearAuthToken();
        store.clear();

        FrontPageActivity.start(this, true, null);
        finishAffinity();
    }
}
