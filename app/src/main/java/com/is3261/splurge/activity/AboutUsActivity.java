package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.base.NavDrawerActivity;

public class AboutUsActivity extends NavDrawerActivity {


    @Override
    public void updateActiveDrawerItem() {
        mNavigationView.getMenu().findItem(mSelectedDrawerItemId).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectedDrawerItemId = R.id.nav_settings;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about_us, mContainer, true);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("About Us");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.notifications:
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

                mDrawerLayout.openDrawer(Gravity.RIGHT);
                return true;
        }
        return true;
    }

    public void onClickSendEmail(View view){

        String aEmailList[] = { "misscooh92@gmail.com" };

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My feedback to Splurge");

        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Feedback Content..");

        startActivity(emailIntent);
        startActivity(Intent.createChooser(emailIntent, "Send your email in:"));

    }

    public void onClickTutorial(View view) {
        startActivity(new Intent(this,WebViewActivity.class));
    }
}
