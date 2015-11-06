package com.is3261.splurge.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.is3261.splurge.Constant;
import com.is3261.splurge.R;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Trip;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class TripCard extends CardView {

    ImageView mAvatar;
    TextView mTripTitle;
    TextView mTimeAgo;

    Trip mTrip;

    public TripCard(Context context) {
        super(context);
        init();
    }

    public TripCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TripCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.card_trip, this, true);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mTripTitle = (TextView) findViewById(R.id.title);
        mTimeAgo = (TextView) findViewById(R.id.subtitle);
    }

    public void update(Trip trip) {
        if (trip == null) return;

        mTrip = trip;
        update();
    }

    //TODO
    private void update() {
        mTripTitle.setText(mTrip.getTitle());
        mTimeAgo.setText(SplurgeHelper.printDate(mTrip.getDate(), Constant.TRIP_DATE_FORMAT));
    }
}
