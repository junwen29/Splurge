package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.dummy.LoadDummyUsers;
import com.is3261.splurge.fragment.SpiltMealFragment;
import com.is3261.splurge.fragment.SpiltMealFragment2;
import com.is3261.splurge.fragment.SplitMealFragment3;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpiltMealActivity extends BaseActivity implements SpiltMealFragment.OnNextSelectListener,
 SpiltMealFragment2.OnNextSelectListener{



    LoadDummyUsers load = new LoadDummyUsers();
    ArrayList<User> currentTripFriends = load.getUserList();
    HashMap<User, Boolean> userMap;

    HashMap<User, Float> userExpense;
    ArrayList<User> selectedFriendList;


    String currency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilt_meal);
        selectedFriendList = new ArrayList<>();
        initMap();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SpiltMealFragment smf1 = new SpiltMealFragment();
        ft.add(R.id.fragment_container_sm, smf1);
        ft.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spilt_meal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        SpiltMealFragment2 fragment2 = new SpiltMealFragment2();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_sm, fragment2);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onNextSelected_sm2(){
        SplitMealFragment3 fragment3 = new SplitMealFragment3();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_sm, fragment3);
        ft.addToBackStack(null);
        ft.commit();
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
