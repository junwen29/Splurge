package com.is3261.splurge.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.is3261.splurge.R;
import com.is3261.splurge.adapter.LocationAdapter;
import com.is3261.splurge.api.CollectionListener;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.TripLocation;

import java.util.Collection;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class SelectLocationFragment extends BaseFragment implements SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private LocationLoader mLoader;
    private Location mLocation;
    private LocationAdapter mAdapter;

    private GridView mGridView;
    private SearchView mSearchView;
    private SwipeRefreshLayout mSwipeLayout;

    private final String TAG = "SelectLocationFragment";

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        loadFoursquareVenues(newText);
        mAdapter.getFilter().filter(newText);

        return false;
    }

    @Override
    public void onRefresh() {
        loadFoursquareVenues(mSearchView.getQuery().toString());
    }

    public interface LocationLoader {
        void load(String query, CollectionListener<TripLocation> listener);

        SearchView getSearchView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLocation = getArguments().getParcelable("location");
        }

        mAdapter = new LocationAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_location, container, false);
        init(v);

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSelectLocation(mAdapter.getItem(position));
            }
        });

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchView = mLoader.getSearchView();
        mSearchView.setOnQueryTextListener(this);
    }

    private void init(View v) {
        mGridView = (GridView) v.findViewById(R.id.gridview);

        initSwipeRefreshLayout(v);

    }

    private void initSwipeRefreshLayout(View view){
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.splurge_primary_dark);
    }

    public static SelectLocationFragment newInstance(Location location) {
        Bundle args = new Bundle();
        args.putParcelable("location", location);

        SelectLocationFragment fragment = new SelectLocationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLoader = (LocationLoader) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LocationLoader");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLoader = null;
    }


    private void loadFoursquareVenues(String query) {
        if (!mSwipeLayout.isRefreshing())
            mSwipeLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeLayout.setRefreshing(true);
                }
            });

        mLoader.load(query, new CollectionListener<TripLocation>() {
            @Override
            public void onResponse(Collection<TripLocation> locations) {
                mSwipeLayout.setRefreshing(false);
                mAdapter.clear();
                mAdapter.addAll(locations);
                mAdapter.addAllUnfilteredItems(locations);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeLayout.setRefreshing(false);
                Log.d(TAG, "Failed to load locations");
            }
        });
    }

    private void onSelectLocation(TripLocation location) {
        Intent i = new Intent();
        i.putExtra("location", location);
        getActivity().setResult(Activity.RESULT_OK, i);
        getActivity().finish();
    }
}