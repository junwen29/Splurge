package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.adapter.ViewPagerAdapter;
import com.is3261.splurge.fragment.AllDebtsFragment;
import com.is3261.splurge.fragment.PendingDebtsFragment;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class DebtActivity extends NavDrawerActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    public void updateActiveDrawerItem() {
        mNavigationView.getMenu().findItem(mSelectedDrawerItemId).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectedDrawerItemId = R.id.nav_debts;

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_debt, mContainer, true);

        init();
        setUpDrawerToggle(mToolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setTitle("Debts");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingDebtsFragment(), "Pending");
        adapter.addFragment(new AllDebtsFragment(), "All");
        viewPager.setAdapter(adapter);
    }

}
