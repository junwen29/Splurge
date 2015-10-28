package com.is3261.splurge.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.SpiltMealActivity;
import com.is3261.splurge.fragment.base.BaseFragment;


public class SpiltMealFragment extends BaseFragment {


    OnNextSelectListener mCallback;
    Switch gst_switch;
    Switch svc_switch;
    EditText gst_input;
    EditText svc_input;
    EditText currency_input;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spilt_meal, container, false);
        gst_switch = (Switch) view.findViewById(R.id.gst_switch);
        svc_switch = (Switch) view.findViewById(R.id.svc_switch);
        gst_input = (EditText) view.findViewById(R.id.gst_input);
        svc_input = (EditText) view.findViewById(R.id.svc_input);
        currency_input = (EditText) view.findViewById(R.id.meal_currency);

        gst_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean isChecked) {

                if(cb.getId() == gst_switch.getId()){
                    if(isChecked){
                        gst_input.setVisibility(View.VISIBLE);
                    }
                    else gst_input.setVisibility(View.GONE);
                }

                if(cb.getId() == svc_switch.getId()){
                    if(isChecked){
                        svc_input.setVisibility(View.VISIBLE);
                    }
                    else svc_input.setVisibility(View.GONE);
                }

            }
        });
        svc_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean isChecked) {

                if(cb.getId() == gst_switch.getId()){
                    if(isChecked){
                        gst_input.setVisibility(View.VISIBLE);
                    }
                    else gst_input.setVisibility(View.GONE);
                }

                if(cb.getId() == svc_switch.getId()){
                    if(isChecked){
                        svc_input.setVisibility(View.VISIBLE);
                    }
                    else svc_input.setVisibility(View.GONE);
                }

            }
        });

        Button mButton = (Button) view.findViewById(R.id.button_sm_next1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpiltMealActivity activity = (SpiltMealActivity) getActivity();
                activity.setCurrency(currency_input.getText().toString());
                mCallback.onNextSelected_sm1();
            }
        });
        return view;
    }

    // Container Activity must implement this interface
    public interface OnNextSelectListener {
        public void onNextSelected_sm1();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnNextSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}
