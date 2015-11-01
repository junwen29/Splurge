package com.is3261.splurge.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        //add in the payment cards
        for (int i = 0; i < numSpenders; i++){
            User spender = mSpenders.get(i);

            PaymentCard paymentCard = new PaymentCard(getContext());
            paymentCard.getSpenderName().setText(spender.getUsername()); //set username as name
            paymentCard.getAvatar().setImageResource(Avatar.getRandomAvatar().getDrawableId()); // put up a random avatar
            paymentCard.setSpender(spender);

            String spendAmount = "$ " + mExpenseMap.get(spender).toString(); // set expense amount
            paymentCard.getSpendAmount().setText(spendAmount);

            mContainer.addView(paymentCard);
        }
    }

    private void mapPayments(){
        boolean hasEmptyFields = false;
        View focusView = null;

        int childViewCounts = mContainer.getChildCount();
        if (childViewCounts == 0){
            return;
        }

        //reset all mappings
        mPaymentMap.clear();

        for (int i = 0; i < childViewCounts; i++){
            PaymentCard paymentCard = (PaymentCard) mContainer.getChildAt(i);
            EditText editText = paymentCard.getPaymentEditText();
            editText.setError(null); //reset error
            String amount = editText.getEditableText().toString();

            //check for empty fields
            if (TextUtils.isEmpty(amount)){
                focusView = editText;
                editText.setError(getString(R.string.error_field_required));
                hasEmptyFields = true;
                break; // exit for loop
            }

            User spender = paymentCard.getSpender();
            mPaymentMap.put(spender, Float.parseFloat(amount));
        }

        if (hasEmptyFields){
            focusView.requestFocus();
        } else {
//            //get all possible users
//            ArrayList<User> allUsers = new ArrayList<>(mSelectedFriends);
//            allUsers.add(mOwner);
//
//            ArrayList<User> spenders = new ArrayList<>();
//            //add user if he is a spender only
//            for (User user : allUsers) {
//                if (mExpenseMap.containsKey(user)){
//                    if (mExpenseMap.get(user) > 0){
//                        spenders.add(user);
//                    }
//                }
            }
//            mCallback.onFragmentThreeNextSelected(mExpenseMap, spenders);
    }
}
