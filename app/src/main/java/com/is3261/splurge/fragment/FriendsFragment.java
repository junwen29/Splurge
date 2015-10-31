package com.is3261.splurge.fragment;

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
import com.is3261.splurge.async_task.LoadFriendsTask;
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
    private FriendsAdapter mFriendsAdapter;
    private LoadFriendsTask mTask = null;
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

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
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
        CollectionListener<User> mListener = new CollectionListener<User>() {
            @Override
            public void onResponse(Collection<User> friends) {
                mSwipeLayout.setRefreshing(false);
                mFriendsAdapter.clear();
                mFriendsAdapter.addAll(friends);
                mFriendsAdapter.notifyDataSetChanged();
                mTask = null;
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mSwipeLayout.setRefreshing(false);
                Log.d(TAG, "Error: " /*+ volleyError.toString()*/);
                mTask = null;
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

            mTask = new LoadFriendsTask(getContext(), mListener, mUserId);
            mTask.execute((Void) null);
        }
    }

    @Override
    public void onRefresh() {
        loadFriends();
    }
}
