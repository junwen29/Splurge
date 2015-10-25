package com.is3261.splurge.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.model.User;

/**
 * Created by junwen29 on 10/22/2015.
 */
public class RequestedFriendCard extends CardView {

    ImageView mAvatar;
    TextView mUsername;
    TextView mEmail;

    User mRequestedFriend;

    public RequestedFriendCard(Context context) {
        super(context);
        init();
    }

    public RequestedFriendCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RequestedFriendCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.card_requested_friend, this, true);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mUsername = (TextView) findViewById(R.id.title);
        mEmail = (TextView) findViewById(R.id.subtitle);
    }

    public void update(User friend){
        if (friend == null) return;

        mRequestedFriend = friend;
        update();
    }

    //TODO
    private void update(){
//        mUsername.setText();
        mUsername.setText(mRequestedFriend.getUsername());
        mEmail.setText(mRequestedFriend.getEmail());
    }
}
