package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.is3261.splurge.R;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Avatar;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.PaymentCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 10/31/2015.
 */
public class SplitMealFragmentFour extends BaseFragment implements View.OnClickListener {

    private View mView;
    private LinearLayout mContainer;
    private Button mFinishButton;

    private ArrayList<User> mSpenders;
    private Map<User,Float> mExpenseMap;
    private Map<User,Float> mPaymentMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_split_meal_fragment4, container, false);
        init(mView);

        mPaymentMap = new HashMap<>();

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_finish:
                break;
        }
    }

    /**
     * called by activity to pass data from fragment 3
     * @param expenseMap contains all expense data
     * @param spenders contains users who spend more than $0.00
     */
    public void setupFragmentFourData(Map<User, Float> expenseMap, ArrayList<User> spenders) {
        this.mExpenseMap = expenseMap;
        mSpenders = spenders;

        addPaymentCards();
    }

    private void addPaymentCards(){
        mContainer.removeAllViews(); // clear all child views

        int numSpenders = mSpenders.size();
        mPaymentMap.clear();

        //add in the payment cards
        for (int i = 0; i < numSpenders; i++){

            PaymentCard paymentCard = new PaymentCard(getContext());
            paymentCard.getSpenderName().setText(mSpenders.get(i).getUsername()); //set username as name
            paymentCard.getAvatar().setImageResource(Avatar.getRandomAvatar().getDrawableId()); // put up a random avatar

            String spendAmount = "$ " + mExpenseMap.get(mSpenders.get(i)).toString(); // set expense amount
            paymentCard.getSpendAmount().setText(spendAmount);

            mContainer.addView(paymentCard);
        }
    }
}
