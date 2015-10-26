package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;
import com.is3261.splurge.fragment.TripFragment;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends NavDrawerActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_trip, mContainer, true);

//        setContentView(R.layout.activity_trip);
        init();
        setUpDrawerToggle(mToolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = new FloatingActionButton.Builder(this).setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_addbtn)).build();


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        ImageView itemIcon = new ImageView(this);
        SubActionButton button1 = itemBuilder.build();

        ImageView itemIcon2 = new ImageView(this);

        SubActionButton button2 = itemBuilder.build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mealSpilt_i = new Intent(TripActivity.this, SpiltMealActivity.class);
                startActivity(mealSpilt_i);
            }
        });




//        ImageView itemIcon3 = new ImageView(this);
        SubActionButton button3 = itemBuilder.build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).addSubActionView(button1)
                                                                            .addSubActionView(button2)
                                                                            .addSubActionView(button3).attachTo(fab).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out: {
                signOut();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TripFragment(), "NO. ONE");
        adapter.addFragment(new TripFragment(), "ITEM TWO");
        adapter.addFragment(new TripFragment(), "THE THIRD");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
