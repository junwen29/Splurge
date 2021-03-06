package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.adapter.ViewPagerAdapter;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.async_task.CreateExpenseTask;
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
        SplitMealFragmentFour.FragmentFourListener,SplitMealFragmentFive.FragmentFiveListener {

    private Toolbar mToolbar;
    private TextView mSubtotal;
    private NonPagingViewPager mPager;
    private ViewPagerAdapter mAdapter;

    private String mGST;
    private String mCurrency;
    private String mSVC;
    private String mDescription;

    MenuItem mAddExpenseMenuItem;

    private static final String TAG = "SpiltMealActivity";

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
        mAdapter.addFragment(fragmentOne,"How did you Splurge ?");
        mAdapter.addFragment(fragmentTwo,"Who are with you ?");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_trip, menu);
        mAddExpenseMenuItem  = menu.findItem(R.id.add_expense);

        // disable the menu item first
        mAddExpenseMenuItem.setVisible(false);
        mAddExpenseMenuItem.setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_expense:
                SplitMealFragmentThree fragment = (SplitMealFragmentThree) mAdapter.getItem(2); // do casting
                fragment.addDishCard();
                break;
        }
        return true;
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
            getSupportActionBar().setTitle("Who are with you ?");
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

            //toggle the enabling of the menu item
            if (position == 2){
                mAddExpenseMenuItem.setVisible(true);
                mAddExpenseMenuItem.setEnabled(true);
            } else {
                mAddExpenseMenuItem.setVisible(false);
                mAddExpenseMenuItem.setEnabled(false);
            }
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

        // enable the button
        mAddExpenseMenuItem.setVisible(true);
        mAddExpenseMenuItem.setEnabled(true);
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

        // disable the button
        mAddExpenseMenuItem.setVisible(false);
        mAddExpenseMenuItem.setEnabled(false);
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

    @Override
    public void onFragmentFiveDone(ArrayList<Expense> expenses) {
        finish();
        for (final Expense expense : expenses) {
            EmptyListener listener = new EmptyListener() {
                @Override
                public void onResponse() {
                    Log.d(TAG, "Registered expense successfully ");
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d(TAG, "Failed to register expense ");
                }
            };
            CreateExpenseTask task = new CreateExpenseTask(expense,listener,this);
            task.execute(null,null,null);
        }
    }
}
