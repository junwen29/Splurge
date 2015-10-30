package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.adapter.ViewPagerAdapter;
import com.is3261.splurge.fragment.ApproveFriendsFragment;
import com.is3261.splurge.fragment.UserPendingFriendsFragment;
import com.is3261.splurge.fragment.FriendsFragment;
import com.is3261.splurge.helper.OwnerStore;

public class ProfileActivity extends NavDrawerActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    public void updateActiveDrawerItem() {
        mNavigationView.getMenu().findItem(R.id.nav_my_friends).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectedDrawerItemId  = R.id.nav_my_friends;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, mContainer, true);

        init();
        setUpDrawerToggle(mToolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FriendsFragment(), "Friends");
        adapter.addFragment(new ApproveFriendsFragment(), "Approve Requests");
        adapter.addFragment(new UserPendingFriendsFragment(), "My Requests");
        viewPager.setAdapter(adapter);
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            String username = (new OwnerStore(this)).getUsername();
            actionBar.setTitle(username);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, AddFriendActivity.class));
            }
        });
    }

}
