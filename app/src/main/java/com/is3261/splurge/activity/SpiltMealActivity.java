package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.adapter.ViewPagerAdapter;
import com.is3261.splurge.fragment.SpiltMealFragmentOne;
import com.is3261.splurge.fragment.SpiltMealFragmentTwo;
import com.is3261.splurge.fragment.SplitMealFragmentFive;
import com.is3261.splurge.fragment.SplitMealFragmentFour;
import com.is3261.splurge.fragment.SplitMealFragmentThree;
import com.is3261.splurge.model.Expense;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.NonPagingViewPager;

import java.util.ArrayList;
import java.util.Map;

public class SpiltMealActivity extends BaseActivity implements SpiltMealFragmentOne.FragmentOneListener,
        SpiltMealFragmentTwo.FragmentTwoListener, SplitMealFragmentThree.FragmentThreeListener,
        SplitMealFragmentFour.FragmentFourListener{

    private Toolbar mToolbar;
    private TextView mSubtotal;
    private NonPagingViewPager mPager;
    private ViewPagerAdapter mAdapter;

    private String mGST;
    private String mCurrency;
    private String mSVC;
    private String mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilt_meal);
        findAllViews();
        setSupportActionBar(mToolbar);

        //configure action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("What is the meal about ?");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SpiltMealFragmentOne    fragmentOne     =   new SpiltMealFragmentOne();
        SpiltMealFragmentTwo    fragmentTwo     =   new SpiltMealFragmentTwo();
        SplitMealFragmentThree  fragmentThree   =   new SplitMealFragmentThree();
        SplitMealFragmentFour   fragmentFour    =   new SplitMealFragmentFour();
        SplitMealFragmentFive   fragmentFive    =   new SplitMealFragmentFive();

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(fragmentOne,"What is the meal about?");
        mAdapter.addFragment(fragmentTwo,"How many friends are eating with you ?");
        mAdapter.addFragment(fragmentThree,"Add an expense");
        mAdapter.addFragment(fragmentFour,"Record payment");
        mAdapter.addFragment(fragmentFive,"Summary");

        mPager.setPagingEnabled(false);
        mPager.setAdapter(mAdapter);
    }

    private void findAllViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPager = (NonPagingViewPager) findViewById(R.id.viewpager);
        mSubtotal = (TextView) findViewById(R.id.subtotal);
    }

    /**
     *  get the string values from fragment one and move to second fragment
     * @param gst gst string value
     * @param currency currency string value
     * @param svc service charge string value
     * @param desc description of expense
     */

    @Override
    public void onFragmentOneNextSelected(String gst, String currency, String svc, String desc) {
        mGST = gst;
        mCurrency = currency;
        mSVC = svc;
        mDescription = desc;
        mPager.setCurrentItem(1, true); //move to next page
        if (getSupportActionBar()!= null)
            getSupportActionBar().setTitle("How many friends are eating with you ?");
        hideKeyboard();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            int position = mPager.getCurrentItem() -1;
            mPager.setCurrentItem(position);
            if (getSupportActionBar()!= null)
                getSupportActionBar().setTitle(mAdapter.getPageTitle(position));

            mSubtotal.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFragmentTwoNextSelected(ArrayList<User> selectedFriends) {
        // pass selected friends to fragment three
        SplitMealFragmentThree fragment = (SplitMealFragmentThree) mAdapter.getItem(2); // do casting
        fragment.setSelectedFriends(selectedFriends);

        Log.d("selected friends", selectedFriends.toString());
        mPager.setCurrentItem(2, true); //move to next page
        if (getSupportActionBar()!= null)
            getSupportActionBar().setTitle("Add an expense");
        hideKeyboard();
    }

    @Override
    public void onFragmentThreeNextSelected(Map<User, Float> expenseMap, ArrayList<User> spenders) {
        SplitMealFragmentFour fragmentFour = (SplitMealFragmentFour) mAdapter.getItem(3); // do casting

        // pass all data
        fragmentFour.setupFragmentFourData(expenseMap, spenders,mGST, mSVC);

        mPager.setCurrentItem(3, true);
        if (getSupportActionBar()!= null)
            getSupportActionBar().setTitle("Record payment");
        hideKeyboard();
    }

    @Override
    public void updateTotalAmount(Float total) {
        mSubtotal.setVisibility(View.VISIBLE);
        String subtotalString = "Total: $" + total.toString();
        mSubtotal.setText(subtotalString);
    }

    @Override
    public void onFragmentFourFinished(ArrayList<Expense> expenses) {
        //hide the subtotal from fragment 4
        mSubtotal.setVisibility(View.GONE);

        SplitMealFragmentFive fragmentFive = (SplitMealFragmentFive) mAdapter.getItem(4); // do casting

        // pass all data
        fragmentFive.setExpenses(expenses);

        mPager.setCurrentItem(4, true);
        if (getSupportActionBar()!= null)
            getSupportActionBar().setTitle("Summary");
        hideKeyboard();
    }
}
