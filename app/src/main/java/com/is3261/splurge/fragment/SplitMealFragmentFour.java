package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.is3261.splurge.R;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

import java.util.Map;

/**
 * Created by junwen29 on 10/31/2015.
 */
public class SplitMealFragmentFour extends BaseFragment implements View.OnClickListener {

    private View mView;
    private LinearLayout mContainer;
    private Button mFinishButton;
    private Map<User,Float> mExpenseMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_split_meal_fragment4, container, false);
        init(mView);

        return mView;
    }

    private void init(View view) {
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mFinishButton = (Button) view.findViewById(R.id.button_finish);

        mFinishButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mContainer.removeAllViews(); // clear all child views

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_finish:
                break;
        }
    }

    public void setExpenseMap(Map<User, Float> expenseMap) {
        this.mExpenseMap = expenseMap;
    }
}
