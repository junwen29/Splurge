package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.ExpensesAdapter;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.Expense;

import java.util.ArrayList;

/**
 * Created by junwen29 on 11/4/2015.
 */
public class SplitMealFragmentFive extends BaseFragment implements View.OnClickListener {

    GridView mGridView;
    Button mDoneButton;

    ExpensesAdapter mAdapter;
    ArrayList<Expense> mExpenses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_split_meal_fragment5, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mDoneButton = (Button) view.findViewById(R.id.button_done);

        mDoneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setExpenses(ArrayList<Expense> expenses) {
        mExpenses = expenses;
        mAdapter = new ExpensesAdapter(expenses, getContext());
        mGridView.setAdapter(mAdapter);
    }
}
