package com.is3261.splurge.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.is3261.splurge.R;
import com.is3261.splurge.model.MenuCategory;

public class MealBillsActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MealBillsActivity";
    private static final String STATE_IS_PLAYING = "isPlaying";
    private static final int UNDEFINED = -1; // for navigation callbacks on toolbar

    private MenuCategory mMenuCategory;
    private Interpolator mInterpolator;
    private boolean mSavedStateIsPlaying;
    private FloatingActionButton mFab;
    private android.widget.Toolbar mToolbar;


    public static Intent getStartIntent(Context context, MenuCategory menuCategory) {
        Intent starter = new Intent(context, MealBillsActivity.class);
        starter.putExtra(MenuCategory.TAG, menuCategory);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate and set the enter transition for this activity.
        final Transition sharedElementEnterTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.menu_enter);
        getWindow().setSharedElementEnterTransition(sharedElementEnterTransition);

        mMenuCategory = getIntent().getParcelableExtra(MenuCategory.TAG);
        mInterpolator = AnimationUtils.loadInterpolator(this,
                android.R.interpolator.fast_out_slow_in);

        if (savedInstanceState != null) {
            mSavedStateIsPlaying = savedInstanceState.getBoolean(STATE_IS_PLAYING);
        }

        super.onCreate(savedInstanceState);
        populate();
    }

    private void populate() {
        if (mMenuCategory == null) {
            Log.w(TAG, "Null mMenuCategory. Finishing");
            finish();
        }

        setTheme(mMenuCategory.getTheme().getStyleId());
        initLayout();
        initToolbar();
    }

    private void initToolbar() {
        if (mMenuCategory == null)
            return;

        mToolbar = (android.widget.Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mMenuCategory.getName());
        mToolbar.setNavigationOnClickListener(this);
        if (mSavedStateIsPlaying) // the toolbar should not have more elevation than the content while playing
            mToolbar.setElevation(0);
    }

    private void initLayout(){
        setContentView(R.layout.activity_meal_bills);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(mSavedStateIsPlaying ? View.GONE : View.VISIBLE);
        mFab.setOnClickListener(this);
        mFab.animate()
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(400)
                .start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                //TODO create expense
                break;
            case UNDEFINED:
                final CharSequence contentDescription = v.getContentDescription();
                if (contentDescription != null && contentDescription.equals(
                        getString(R.string.up))) {
                    onBackPressed();
                    break;
                }
                break;
        }
    }
}
