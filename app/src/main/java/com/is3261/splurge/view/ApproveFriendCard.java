package com.is3261.splurge.view;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.api.SplurgeApi;
import com.is3261.splurge.api.request.FriendshipRequest;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.User;

/**
 * Created by junwen29 on 10/25/2015.
 */
public class ApproveFriendCard extends CardView implements View.OnClickListener {

    ImageView mAvatar;
    TextView mUsername;
    TextView mEmail;

    Button mConfirmBtn;
    Button mRejectBtn;

    private Context mContext;
    private EmptyListener mListener;
    private ApproveFriendTask mTask = null;
    private boolean mApprove;

    User mPendingFriend;

    public ApproveFriendCard(Context context) {
        super(context);
        init(context);
    }

    public ApproveFriendCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ApproveFriendCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mListener = new EmptyListener() {
            @Override
            public void onResponse() {
                if (mApprove)
                    Snackbar.make(ApproveFriendCard.this, "Friend Confirmed", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(ApproveFriendCard.this, "Friend Request Rejected", Snackbar.LENGTH_LONG).show();

                mConfirmBtn.setVisibility(GONE);
                mRejectBtn.setVisibility(GONE);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                NetworkResponse response = volleyError.networkResponse;
                if (response != null && response.data != null){
                    String message = "";
                    switch (response.statusCode){
                        case 400: //conflict
                            message = "Unable to confirm friend, try again later.";
                            break;
                    }
                    Snackbar.make(ApproveFriendCard.this, message, Snackbar.LENGTH_LONG).show();
                }
            }
        };

        LayoutInflater.from(getContext()).inflate(R.layout.card_approve_friend, this, true);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mUsername = (TextView) findViewById(R.id.title);
        mEmail = (TextView) findViewById(R.id.subtitle);

        mConfirmBtn = (Button) findViewById(R.id.btn_confirm);
        mRejectBtn = (Button) findViewById(R.id.btn_reject);
    }

    public void update(User friend){
        if (friend == null) return;

        mPendingFriend = friend;
        update();
    }

    //TODO
    private void update(){
//        mUsername.setText();
        mUsername.setText(mPendingFriend.getUsername());
        mEmail.setText(mPendingFriend.getEmail());

        mConfirmBtn.setOnClickListener(this);
        mRejectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        OwnerStore ownerStore = new OwnerStore(mContext);
        String userId = ownerStore.getOwnerId();
        String friendId = Long.toString(mPendingFriend.id);

        switch (v.getId()){
            case R.id.btn_confirm:
                mTask = new ApproveFriendTask(userId,friendId, true);
                mTask.execute(null,null,null);
                break;
            case R.id.btn_reject:
                mTask = new ApproveFriendTask(userId,friendId, false);
                mTask.execute(null,null,null);
                break;
            default:
                break;
        }
    }

    public class ApproveFriendTask extends AsyncTask<Void,Void,Void> {
        private String mUserId;
        private String mFriendId;

        ApproveFriendTask(String userId, String friendId, boolean approve) {
            mUserId = userId;
            mFriendId = friendId;
            mApprove = approve;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
                if (mApprove)
                    SplurgeApi.getInstance(mContext).enqueue(FriendshipRequest.approve(mUserId, mFriendId, mListener));
                else {
                    SplurgeApi.getInstance(mContext).enqueue(FriendshipRequest.reject(mUserId, mFriendId, mListener));
                }
            } catch (InterruptedException ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mTask = null;
        }

        @Override
        protected void onCancelled() {
            mTask = null;
        }
    }
}