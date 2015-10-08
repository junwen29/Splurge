package com.is3261.splurge.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.MenuCategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuSelectionFragment extends Fragment {

    private MenuCategoryAdapter mMenuAdapter;

    public static MenuSelectionFragment newInstance() {
        return new MenuSelectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupMenuGrid((GridView) view.findViewById(R.id.menus));
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupMenuGrid(GridView menuView) {
        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = getActivity();
//                startQuizActivityWithTransition(activity, view.findViewById(R.id.menu_title),
//                        mMenuAdapter.getItem(position));
            }
        });
        mMenuAdapter = new MenuCategoryAdapter(getActivity());
        menuView.setAdapter(mMenuAdapter);
    }
}
