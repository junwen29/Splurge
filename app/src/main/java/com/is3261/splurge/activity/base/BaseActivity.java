package com.is3261.splurge.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.api.SplurgeApi;

/**
 * Created by junwen29 on 10/7/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private SplurgeApi mApi;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public SplurgeApi getSplurgeApi() {
        if (mApi == null)
            mApi = SplurgeApi.getInstance(this);
        return mApi;
    }

    public void showErrorMessage() {
        showErrorMessage(R.string.error_default);
    }

    public void showErrorMessage(int resId) {
        showErrorMessage(getString(resId));
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showErrorMessage(VolleyError error) {
        error.printStackTrace();
        if (error instanceof NoConnectionError) {
            showErrorMessage(R.string.error_network);
        } else {
            showErrorMessage();
        }
    }
}
