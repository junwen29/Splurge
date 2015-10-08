package com.is3261.splurge.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.is3261.splurge.fragment.MenuSelectionFragment;
import com.is3261.splurge.R;
import com.is3261.splurge.helper.OwnerStore;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setUpToolbar();

        if (savedInstanceState == null) {
            attachMenuGridFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void signOut(){
        OwnerStore store = new OwnerStore(this);
        store.clearAuthToken();
        store.clear();

        FrontPageActivity.start(this, true, null);
        finishAffinity();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setActionBar(toolbar);
        //noinspection ConstantConditions
        getActionBar().setDisplayShowTitleEnabled(false);
        OwnerStore ownerStore = new OwnerStore(this);
        String username = ownerStore.getUsername();
        ((TextView) toolbar.findViewById(R.id.username)).setText(username);
    }


    private void attachMenuGridFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, MenuSelectionFragment.newInstance())
                .commit();
    }
}
