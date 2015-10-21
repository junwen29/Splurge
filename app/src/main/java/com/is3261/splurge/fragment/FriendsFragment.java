package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.is3261.splurge.R;
import com.is3261.splurge.fragment.base.BaseFragment;

/**
 * Created by junwen29 on 10/18/2015.
 */
public class FriendsFragment extends BaseFragment{

    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        init(v);
        return v;
    }

    private void init(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }
}
