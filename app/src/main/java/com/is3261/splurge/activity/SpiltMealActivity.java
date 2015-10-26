package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.fragment.SpiltMealFragment;
import com.is3261.splurge.fragment.SpiltMealFragment2;
import com.is3261.splurge.fragment.SplitMealFragment3;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.List;

public class SpiltMealActivity extends BaseActivity {

    public ViewPager viewPager;
    User selected_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilt_meal);
        selected_user = new User();
        viewPager = (ViewPager) findViewById(R.id.viewpager_spiltmeal);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SpiltMealFragment(), "basicInfo");
        adapter.addFragment(new SpiltMealFragment2(), "TripMember");
        adapter.addFragment(new SplitMealFragment3(), "Summary");
        viewPager.setAdapter(adapter);


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


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<BaseFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(BaseFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }

    public User getUser(){
        System.out.println("***********SELECTED USER GETUSER():" + selected_user.getUsername());
        return selected_user;
    }

    public void setUser(User user){
        this.selected_user = user;
        System.out.println("***************** User set: " + selected_user.getUsername() );
    }

}
