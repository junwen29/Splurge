package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.is3261.splurge.R;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.Owner;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.DishCard;

import java.util.ArrayList;

public class SplitMealFragmentThree extends BaseFragment {

    private View mView;
    private LinearLayout mContainer;
    private FloatingActionButton mFab;
    private int mDishIndex = 0;
    private ArrayList<User> mSelectedFriends;
    private Owner mOwner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_split_meal_fragment3, container, false);
        init(mView);

        mSelectedFriends = new ArrayList<>();
        OwnerStore ownerStore = new OwnerStore(getContext());
        Long id = Long.parseLong(ownerStore.getOwnerId());
        mOwner = new Owner(id);
        mOwner.setUsername(ownerStore.getUsername());

        return mView;
    }

    /**
     * find all views and initialise them
     */
    private void init(View view){
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishCard();
            }
        });
    }

    private void addDishCard(){
        if (mContainer == null) return;

        mDishIndex++;
        final DishCard dishCard = new DishCard(getContext());
        dishCard.setTitle("Dish " + mDishIndex);

        final Spinner spinner = dishCard.getSpinner();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        adapter.add(mOwner);
        adapter.addAll(mSelectedFriends);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinner.setSelection(position,true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dishCard.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainer.removeView(dishCard);

                // If there are no rows remaining, show the empty view.
                if (mContainer.getChildCount() == 0) {
                    mView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });

        mContainer.addView(dishCard);
        dishCard.requestFocus();
        // Hide the "empty" view since there is now at least one item in the list.
        mView.findViewById(R.id.empty).setVisibility(View.GONE);
    }

    public void setSelectedFriends(ArrayList<User> selectedFriends){
        if (selectedFriends != null){
            mSelectedFriends = selectedFriends;
        }
    }
}
