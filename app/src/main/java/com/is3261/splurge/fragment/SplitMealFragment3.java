package com.is3261.splurge.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.SpiltMealActivity;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SplitMealFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SplitMealFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplitMealFragment3 extends BaseFragment {

    TextView mTv;

    User selectedUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectedUser = new User();

        View view = inflater.inflate(R.layout.fragment_split_meal_fragment3, container, false);
        SpiltMealActivity activity = (SpiltMealActivity) getActivity();


        return view;
       }


    public User getUser(){
        return selectedUser;
    }

    public void setUser(User user){
        this.selectedUser = user;
    }

    public TextView getmTv() {
        return mTv;
    }
}
