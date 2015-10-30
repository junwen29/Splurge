package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.adapter.ViewPagerAdapter;
import com.is3261.splurge.dummy.LoadDummyUsers;
import com.is3261.splurge.fragment.SpiltMealFragmentOne;
import com.is3261.splurge.fragment.SpiltMealFragmentTwo;
import com.is3261.splurge.fragment.SplitMealFragmentThree;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.NonPagingViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpiltMealActivity extends BaseActivity implements SpiltMealFragmentOne.OnNextSelectListener,
        SpiltMealFragmentTwo.OnNextSelectListener{

    LoadDummyUsers load = new LoadDummyUsers();
    ArrayList<User> currentTripFriends = load.getUserList();
    HashMap<User, Boolean> userMap;

    HashMap<User, Float> userExpense;
    ArrayList<User> selectedFriendList;
    String currency;

    private Toolbar mToolbar;
    private NonPagingViewPager mPager;
    private List<User> mFriends;
    private ViewPagerAdapter mAdapter;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilt_meal);
        findAllViews();
        setSupportActionBar(mToolbar);

        //configure action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Add an Expense");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SpiltMealFragmentOne    fragmentOne =   new SpiltMealFragmentOne();
        SpiltMealFragmentTwo    fragmentTwo =   new SpiltMealFragmentTwo();
        SplitMealFragmentThree  fragmentThree = new SplitMealFragmentThree();

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(fragmentOne,"first page");
        mAdapter.addFragment(fragmentTwo,"second page");
        mAdapter.addFragment(fragmentThree,"third page");
        mPager.setAdapter(mAdapter);

        selectedFriendList = new ArrayList<>();
        initMap();
    }

    private void findAllViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPager = (NonPagingViewPager) findViewById(R.id.viewpager);
    }

    public void initMap() {

        userMap = new HashMap<>();

        userExpense = new HashMap<>();

        for (int i = 0; i < currentTripFriends.size(); i++){
            userMap.put(currentTripFriends.get(i), Boolean.FALSE);
        }
        System.out.println("Finish InitMap : " + userMap.toString());

    }

    @Override
    public void onNextSelected_sm1(){
//        SpiltMealFragmentTwo fragment2 = new SpiltMealFragmentTwo();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container_sm, fragment2);
//        ft.addToBackStack(null);
//        ft.commit();

    }

    @Override
    public void onNextSelected_sm2(){
//        SplitMealFragmentThree fragment3 = new SplitMealFragmentThree();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container_sm, fragment3);
//        ft.addToBackStack(null);
//        ft.commit();
    }

    public void setUpUserItem(HashMap<User, Boolean> selectedUser ){
        if(userExpense.size() == 0 ) {
            Iterator it = selectedUser.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<User, Boolean> pair = (Map.Entry) it.next();
                if (pair.getValue()) {
                    userExpense.put(pair.getKey(), Float.valueOf(0));
                }
            }
        }

    }

    public void addExpense(User user, Float amount){
        userExpense.put(user, userExpense.get(user) + amount);
    }



    public void setUserMap(HashMap<User, Boolean> userMap){
        this.userMap = userMap;
    }


    public HashMap<User, Boolean> getUserMap() {
        return userMap;
    }

    public void updateEntry(User key, Boolean newStatus){
        userMap.put(key, newStatus);
    }

    public ArrayList<User> getCurrentTripFriends() {

        return currentTripFriends;
    }

    public void setCurrentTripFriends(ArrayList<User> currentTripFriends) {
        this.currentTripFriends = currentTripFriends;
    }

    public ArrayList<User> getSelectedFriendList() {
        return selectedFriendList;
    }

    public void setSelectedFriendList(ArrayList<User> selectedFriendList) {
        this.selectedFriendList = selectedFriendList;
    }

    public HashMap<User, Float> getUserExpense() {
        return userExpense;
    }

    public void setUserExpense(HashMap<User, Float> userExpense) {
        this.userExpense = userExpense;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
