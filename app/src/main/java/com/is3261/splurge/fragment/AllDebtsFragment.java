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
import com.is3261.splurge.adapter.DebtsAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.async_task.LoadAllDebtsTask;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.Expense;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class AllDebtsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private GridView mGridView;
    private SwipeRefreshLayout mSwipeLayout;

    private String mUserId;
    private DebtsAdapter mAdapter;
    private LoadAllDebtsTask mTask = null;
    private static final String TAG = "PendingDebtsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending_debts, container, false);
        init(v);

        //load requests
        onRefresh();
        return v;
    }

    private void init(View view){
        initSwipeRefreshLayout(view);

        mGridView = (GridView) view.findViewById(R.id.gridview);
        mAdapter = new DebtsAdapter(new ArrayList<Expense>(), getContext());
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
        loadAllDebts();
    }

    private void loadAllDebts(){
        CollectionListener<Expense> mListener = new CollectionListener<Expense>() {
            @Override
            public void onResponse(Collection<Expense> debts) {
                mSwipeLayout.setRefreshing(false);
                mAdapter.clear();
                mAdapter.addAll(debts);
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

            mTask = new LoadAllDebtsTask(getContext(), mListener, mUserId);
            mTask.execute((Void) null);
        }
    }
}
