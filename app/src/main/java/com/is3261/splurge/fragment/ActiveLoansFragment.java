package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.adapter.LoansAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.async_task.LoadLoansTask;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.Expense;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class ActiveLoansFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private GridView mGridView;
    private SwipeRefreshLayout mSwipeLayout;

    private String mUserId;
    private LoansAdapter mAdapter;
    private LoadLoansTask mTask = null;
    private static final String TAG = "ActiveLoansFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_active_loans, container, false);
        init(v);

        //load requests
        onRefresh();
        return v;
    }

    private void init(View view){
        initSwipeRefreshLayout(view);

        mGridView = (GridView) view.findViewById(R.id.gridview);
        mAdapter = new LoansAdapter(new ArrayList<Expense>(), getContext());
        mGridView.setAdapter(mAdapter);

        OwnerStore ownerStore = new OwnerStore(getContext());
        mUserId = ownerStore.getOwnerId();
    }

    private void initSwipeRefreshLayout(View view){
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.splurge_primary_dark);
    }

    @Override
    public void onRefresh() {
        loadLoans();
    }

    private void loadLoans(){
        CollectionListener<Expense> mListener = new CollectionListener<Expense>() {
            @Override
            public void onResponse(Collection<Expense> loans) {
                mSwipeLayout.setRefreshing(false);
                mAdapter.clear();
                mAdapter.addAll(loans);
                mAdapter.notifyDataSetChanged();
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

            mTask = new LoadLoansTask(getContext(), mListener, mUserId);
            mTask.execute((Void) null);
        }
    }
}

