package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.adapter.ViewPagerAdapter;
import com.is3261.splurge.fragment.ActiveLoansFragment;
import com.is3261.splurge.fragment.AllLoansFragment;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class LoanActivity extends NavDrawerActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    public void updateActiveDrawerItem() {
        mNavigationView.getMenu().findItem(mSelectedDrawerItemId).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectedDrawerItemId = R.id.nav_loans;

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_loan, mContainer, true);

        init();
        setUpDrawerToggle(mToolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Loans");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActiveLoansFragment(), "Active");
        adapter.addFragment(new AllLoansFragment(), "All");
        viewPager.setAdapter(adapter);
    }
}
