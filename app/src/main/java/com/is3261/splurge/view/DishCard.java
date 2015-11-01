package com.is3261.splurge.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.is3261.splurge.R;

/**
 * Created by junwen29 on 10/31/2015.
 */
public class DishCard extends CardView {

    private Context mContext;
    private TextView mTitle;
    private Spinner mSpinner;
    private EditText mEditText;
    private ImageButton mImageButton;

    public DishCard(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DishCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public DishCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_dish, this, true);
        mTitle = (TextView) v.findViewById(R.id.title);
        mSpinner = (Spinner) v.findViewById(R.id.spinner);
        mEditText = (EditText) v.findViewById(R.id.amount);
        mImageButton = (ImageButton) v.findViewById(R.id.delete_button);
    }

    public void setTitle(String title){
        if (TextUtils.isEmpty(title)) return;

        mTitle.setText(title);
    }

    public ImageButton getImageButton() {
        return mImageButton;
    }

    public Spinner getSpinner() {
        return mSpinner;
    }

    public EditText getEditText() {
        return mEditText;
    }
}
