package com.is3261.splurge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.adapter.CheckableFriendsAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.async_task.LoadFriendsTask;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.Collection;

public class SpiltMealFragmentTwo extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public interface FragmentTwoListener {
        void onFragmentTwoNextSelected(ArrayList<User> selectedFriends);
    }

    private CheckableFriendsAdapter mFriendsAdapter;
    private SwipeRefreshLayout mSwipeLayout;

    private LoadFriendsTask mTask;
    private static final String TAG = "SpiltMealFragmentTwo";
    private FragmentTwoListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //find views
        View view = inflater.inflate(R.layout.fragment_spilt_meal_fragment2, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.listview);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        Button mButton = (Button) view.findViewById(R.id.button_sm_next2);

        //init swipe refresh
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.splurge_primary_dark);

        ArrayList<User> mFriends = new ArrayList<>();
        mFriendsAdapter = new CheckableFriendsAdapter(mFriends, getContext());
        mListView.setAdapter(mFriendsAdapter);

        loadFriends();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get selected friends
                mCallback.onFragmentTwoNextSelected(mFriendsAdapter.getSelectedFriends());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentTwoListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement FragmentTwoListener");
        }
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
                mFriendsAdapter.clear();
                mFriendsAdapter.notifyDataSetChanged();
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

            String userId = (new OwnerStore(getContext())).getOwnerId();
            mTask = new LoadFriendsTask(getContext(), mListener, userId);
            mTask.execute((Void) null);
        }
    }

    @Override
    public void onRefresh() {
        loadFriends();
    }
}
