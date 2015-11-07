package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.is3261.splurge.R;

public class AboutUsActivity extends AppCompatActivity {


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

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
}
