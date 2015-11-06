package com.is3261.splurge.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.is3261.splurge.Constant;
import com.is3261.splurge.R;
import com.is3261.splurge.helper.SplurgeHelper;
import com.is3261.splurge.model.Avatar;
import com.is3261.splurge.model.Expense;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class DebtCard extends CardView {

    private AvatarView mSpenderAvatar;
    private TextView mSpenderName;

    private TextView mAmount;
    private TextView mDate;
    private TextView mStatus;

    public DebtCard(Context context) {
        super(context);
        init();
    }

    public DebtCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DebtCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.card_debt, this, true);

        mSpenderAvatar = (AvatarView) findViewById(R.id.avatar_spender);
        mSpenderName = (TextView) findViewById(R.id.spender_name);

        mAmount = (TextView) findViewById(R.id.amount);
        mDate = (TextView) findViewById(R.id.date);
        mStatus = (TextView) findViewById(R.id.status);
    }

    public void update(Expense expense){

        mSpenderAvatar.setImageResource(Avatar.getRandomAvatar().getDrawableId());
        mSpenderName.setText(expense.getSpender().getUsername());

        String amount = "$" + expense.getAmount();
        mAmount.setText(amount);

        String date = SplurgeHelper.printDate(expense.getCreatedAt(), Constant.EXPENSE_DATE_FORMAT);
        mDate.setText(date);

        String status = expense.isSettled() ? "Settled" : "Not settled";
        mStatus.setText(status);
        if (expense.isSettled())
            mStatus.setTextColor(getResources().getColor(R.color.theme_green_primary));
        else
            mStatus.setTextColor(getResources().getColor(R.color.theme_red_primary));
    }
}
