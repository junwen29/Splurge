package com.is3261.splurge.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.adapter.FriendsAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.api.request.FriendshipRequest;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by junwen29 on 10/18/2015.
 *
 * show all the user's approved friends
 *
 */
public class FriendsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private FriendsAdapter mFriendsAdapter;
    private LoadFriendsTask mTask = null;
    private CollectionListener<User> mListener;
    private String mUserId;
    private static final String TAG = "FriendsFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        init(v);

        //load requests
        loadFriends();
        return v;
    }

    private void init(View view){
        initSwipeRefreshLayout(view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mFriendsAdapter = new FriendsAdapter(new ArrayList<User>(), getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        mRecyclerView.setAdapter(mFriendsAdapter);

        OwnerStore ownerStore = new OwnerStore(getContext());
        mUserId = ownerStore.getOwnerId();
    }

    private void initSwipeRefreshLayout(View view){
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.splurge_primary_dark);
    }

    private void loadFriends(){
        mListener = new CollectionListener<User>() {
            @Override
            public void onResponse(Collection<User> friends) {
                mSwipeLayout.setRefreshing(false);
                mFriendsAdapter.clear();
                mFriendsAdapter.addAll(friends);
                mFriendsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mSwipeLayout.setRefreshing(false);
                Log.d(TAG, "Error: " /*+ volleyError.toString()*/);
            }
        };

        if (mTask == null){
            if (!mSwipeLayout.isRefreshing())
                mSwipeLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeLayout.setRefreshing(true);
                    }
                });

            mTask = new LoadFriendsTask();
            mTask.execute((Void) null);
        }
    }

    @Override
    public void onRefresh() {
        loadFriends();
    }

    public class LoadFriendsTask extends AsyncTask<Void,Void,Void> {

        public LoadFriendsTask() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
                getSplurgeApi().enqueue(FriendshipRequest.loadFriends(mUserId, mListener));
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
