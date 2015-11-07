package com.is3261.splurge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.is3261.splurge.R;

public class AboutUsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
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
