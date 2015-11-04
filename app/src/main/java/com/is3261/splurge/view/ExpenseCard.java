package com.is3261.splurge.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.model.Avatar;
import com.is3261.splurge.model.Expense;

/**
 * Created by junwen29 on 11/4/2015.
 */
public class ExpenseCard extends CardView {

    private AvatarView mBorrowerAvatar;
    private TextView mBorrowerName;

    private AvatarView mSpenderAvatar;
    private TextView mSpenderName;

    private TextView mAmount;

    public ExpenseCard(Context context) {
        super(context);
        init();
    }

    public ExpenseCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpenseCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.card_expense, this, true);

        mBorrowerAvatar = (AvatarView) findViewById(R.id.avatar_borrower);
        mBorrowerName = (TextView) findViewById(R.id.borrower_name);

        mSpenderAvatar = (AvatarView) findViewById(R.id.avatar_spender);
        mSpenderName = (TextView) findViewById(R.id.spender_name);

        mAmount = (TextView) findViewById(R.id.amount);

    }

    public void update(Expense expense){
        mBorrowerAvatar.setImageResource(Avatar.getRandomAvatar().getDrawableId());
        mBorrowerName.setText(expense.getBorrower().getUsername());

        mSpenderAvatar.setImageResource(Avatar.getRandomAvatar().getDrawableId());
        mSpenderName.setText(expense.getSpender().getUsername());

        String amount = "$" + expense.getAmount();
        mAmount.setText(amount);
    }
}
