package com.is3261.splurge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.is3261.splurge.R;
import com.is3261.splurge.fragment.base.BaseFragment;


public class SpiltMealFragmentOne extends BaseFragment {

    // Container Activity must implement this interface
    public interface FragmentOneListener {
        void onFragmentOneNextSelected(String gst, String currency, String svc, String desc);
    }

    FragmentOneListener mCallback;
    Switch gst_switch;
    Switch svc_switch;
    EditText gst_input;
    EditText svc_input;
    EditText currency_input;
    EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spilt_meal, container, false);
        gst_switch = (Switch) view.findViewById(R.id.gst_switch);
        svc_switch = (Switch) view.findViewById(R.id.svc_switch);
        gst_input = (EditText) view.findViewById(R.id.gst_input);
        svc_input = (EditText) view.findViewById(R.id.svc_input);
        currency_input = (EditText) view.findViewById(R.id.meal_currency);
        description = (EditText) view.findViewById(R.id.description);

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
                attemptNext();
            }
        });
        return view;
    }

    private void attemptNext(){
        //reset errors
        currency_input.setError(null);

        String gst = gst_input.getEditableText().toString();
        String currency = currency_input.getEditableText().toString();
        String svc = svc_input.getEditableText().toString();
        String desc = description.getEditableText().toString();
        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(currency)){
            currency_input.setError(getString(R.string.error_field_required));
            focusView = currency_input;
            cancel = true;
        }

        if (!TextUtils.isEmpty(gst) && !isValidGstSvc(gst)){
            gst_input.setError(getString(R.string.error_invalid_gst));
            focusView = gst_input;
            cancel = true;
        }

        if (!TextUtils.isEmpty(svc) && !isValidGstSvc(svc)){
            svc_input.setError(getString(R.string.error_invalid_svc));
            focusView = gst_input;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        } else {
            mCallback.onFragmentOneNextSelected(gst, currency, svc, desc);
        }
    }

    /**
     *
     * @param value value
     * @return true if gst/svc format is correct
     */

    private boolean isValidGstSvc(String value){
        int gstValue = Integer.parseInt(value);
        return gstValue < 100;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentOneListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement FragmentOneListener");
        }
    }
}
