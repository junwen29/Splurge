package com.is3261.splurge.fragment;

import android.content.Context;
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
import com.is3261.splurge.helper.GreedyHelper;
import com.is3261.splurge.model.Avatar;
import com.is3261.splurge.model.Expense;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.PaymentCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 10/31/2015.
 */
public class SplitMealFragmentFour extends BaseFragment implements View.OnClickListener {

    public interface FragmentFourListener{
        void updateTotalAmount(Float total);

        void onFragmentFourFinished(ArrayList<Expense> expenses);
    }

    private View mView;
    private LinearLayout mContainer;
    private Button mFinishButton;

    private ArrayList<User> mSpenders;
    private Map<User,Float> mExpenseMap;
    private Map<User,Float> mTotalExpenseMap;
    private Map<User,Float> mPaymentMap;
    private Float mTotal;
    private String mGST;
    private String mSVC;

    private FragmentFourListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_split_meal_fragment4, container, false);
        init(mView);

        mPaymentMap = new HashMap<>();
        mTotalExpenseMap = new HashMap<>();

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
                mapPayments();
                break;
        }
    }

    /**
     * called by activity to pass data from fragment 3
     * @param expenseMap contains all expense data
     * @param spenders contains users who spend more than $0.00
     */
    public void setupFragmentFourData(Map<User, Float> expenseMap, ArrayList<User> spenders,
                                      String gst, String svc) {
        this.mExpenseMap = expenseMap;
        mSpenders = spenders;
        mGST = gst;
        mSVC = svc;

        addPaymentCards();
    }

    private void addPaymentCards(){
        mContainer.removeAllViews(); // clear all child views

        int numSpenders = mSpenders.size();
        mTotal = Float.valueOf("0.00");

        //add in the payment cards
        for (int i = 0; i < numSpenders; i++){
            User spender = mSpenders.get(i);

            PaymentCard paymentCard = new PaymentCard(getContext());
            paymentCard.getSpenderName().setText(spender.getUsername()); //set username as name
            paymentCard.getAvatar().setImageResource(Avatar.getRandomAvatar().getDrawableId()); // put up a random avatar
            paymentCard.setSpender(spender);

            Float spend = mExpenseMap.get(spender);
            if (spend == null){
                spend = Float.valueOf("0.00");
            }

            String spendAmount = "$ " + spend.toString(); // set expense amount
            paymentCard.getSpendAmount().setText(spendAmount);

            //calculate total amount after GST and SVC
            Float total = spend;
            if (!TextUtils.isEmpty(mSVC)){
                Float svc = Float.parseFloat(mSVC) / 100 + 1; // service charge is taxable by gst
                total *= svc;
            }

            if (!TextUtils.isEmpty(mGST)){
                Float gst = Float.parseFloat(mGST) / 100 + 1;
                total *= gst;
            }

            String totalString = "$ " + total.toString();
            paymentCard.getTotalAmount().setText(totalString);

            mTotalExpenseMap.put(spender,total); // add to total expense map

            mContainer.addView(paymentCard);
            mTotal += total;
        }

        //callback to activity to update total amount on toolbar
        mCallback.updateTotalAmount(mTotal);
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
        } else if (checkPaymentTally()){
            //proceed
            GreedyHelper greedy = new GreedyHelper(mSpenders, mTotalExpenseMap, mPaymentMap);
            ArrayList<Expense> expenses = greedy.constructBills();
            mCallback.onFragmentFourFinished(expenses);
        } else {
            //error
            Snackbar.make(mContainer,"Payments do not tally.", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @return true if all payments tally after calculations
     */

    private boolean checkPaymentTally(){
        Float total = mTotal;
        for (User spender : mSpenders) {
            if (mPaymentMap.containsKey(spender)){
                total -= mPaymentMap.get(spender);
            }
        }

        //check if total becomes zero
        return total == 0;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentFourListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement FragmentFourListener");
        }
    }
}
