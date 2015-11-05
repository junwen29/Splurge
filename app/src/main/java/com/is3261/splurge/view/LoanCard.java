package com.is3261.splurge.view;

/**
 * Created by junwen29 on 11/5/2015.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.is3261.splurge.Constant;
import com.is3261.splurge.R;
import com.is3261.splurge.api.EmptyListener;
import com.is3261.splurge.async_task.SettleLoanTask;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Avatar;
import com.is3261.splurge.model.Expense;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class LoanCard extends CardView implements View.OnClickListener {

    private AvatarView mBorrowerAvatar;
    private TextView mBorrowerName;

    private TextView mAmount;
    private TextView mDate;
    private TextView mStatus;

    private Button mSettleUp;

    private Expense mExpense;
    private static final String TAG = "LoanCard";

    public LoanCard(Context context) {
        super(context);
        init();
    }

    public LoanCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoanCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.card_loan, this, true);

        mBorrowerAvatar = (AvatarView) findViewById(R.id.avatar_borrower);
        mBorrowerName = (TextView) findViewById(R.id.borrower_name);

        mAmount = (TextView) findViewById(R.id.amount);
        mDate = (TextView) findViewById(R.id.date);
        mStatus = (TextView) findViewById(R.id.status);

        mSettleUp = (Button) findViewById(R.id.btn_settle);
        mSettleUp.setOnClickListener(this);
    }

    public void update(Expense expense){
        mExpense  = expense;
        mBorrowerAvatar.setImageResource(Avatar.getRandomAvatar().getDrawableId());
        mBorrowerName.setText(expense.getBorrower().getUsername());

        String amount = "$" + expense.getAmount();
        mAmount.setText(amount);

        String date = SplurgeHelper.printDate(expense.getCreatedAt(), Constant.EXPENSE_DATE_FORMAT);
        mDate.setText(date);

        String status = expense.isSettled() ? "Settled" : "Not settled";
        mStatus.setText(status);
        if (expense.isSettled()) {
            mStatus.setTextColor(getResources().getColor(R.color.theme_green_primary));
            mSettleUp.setEnabled(false);
        }
        else {
            mStatus.setTextColor(getResources().getColor(R.color.theme_red_primary));
            mSettleUp.setEnabled(true);
        }
    }

    @Override
    public void onClick(final View v) {
        EmptyListener listener = new EmptyListener() {
            @Override
            public void onResponse() {
                v.setEnabled(false);
                Log.d(TAG, "Successfully settled up");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Failed to settled up");
                v.setEnabled(true);
            }
        };

        SettleLoanTask task = new SettleLoanTask(Long.toString(mExpense.id), getContext(), listener);
        task.execute(null, null, null);
    }
}

