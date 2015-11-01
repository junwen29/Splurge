package com.is3261.splurge.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.model.User;

/**
 * Created by junwen29 on 11/1/2015.
 */
public class PaymentCard extends CardView {

    private AvatarView mAvatar;
    private TextView mSpenderName;
    private TextView mSpendAmount;
    private EditText mPaymentEditText;

    private User mSpender;

    public PaymentCard(Context context) {
        super(context);
        init(context);
    }

    public PaymentCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PaymentCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.card_payment, this, true);
        mAvatar = (AvatarView) view.findViewById(R.id.avatar);
        mSpenderName = (TextView) view.findViewById(R.id.spender_name);
        mSpendAmount = (TextView) view.findViewById(R.id.spend_amount);
        mPaymentEditText = (EditText) view.findViewById(R.id.pay_amount);
    }

    public AvatarView getAvatar() {
        return mAvatar;
    }

    public TextView getSpenderName() {
        return mSpenderName;
    }

    public TextView getSpendAmount() {
        return mSpendAmount;
    }

    public EditText getPaymentEditText() {
        return mPaymentEditText;
    }

    public User getSpender() {
        return mSpender;
    }

    public void setSpender(User mSpender) {
        this.mSpender = mSpender;
    }
}
