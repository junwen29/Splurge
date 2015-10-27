package com.is3261.splurge.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.SpiltMealActivity;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SplitMealFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SplitMealFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplitMealFragment3 extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_split_meal_fragment3, container, false);
        SpiltMealActivity activity = (SpiltMealActivity) getActivity();


        return view;
       }


    public class UsersAdapter extends ArrayAdapter<User> {

        User user;

        public UsersAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sm_member_expense_detail, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvCurrency = (TextView) convertView.findViewById(R.id.tvCurrency);
            TextView tvExpense = (TextView) convertView.findViewById(R.id.tvExpense);

            // Populate the data into the template view using the data object
            tvName.setText(user.getUsername());

            // Return the completed view to render on screen
            return convertView;
        }
    }


}
