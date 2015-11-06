package com.is3261.splurge.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.is3261.splurge.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by junwen29 on 11/6/2015.
 */
public class TripDateSelectDialog extends DialogFragment {

    public interface TripDateSelectorListener {
        void onFinish(Date date);
    }

    public static TripDateSelectDialog newInstance() {
        return new TripDateSelectDialog();
    }

    private TripDateSelectorListener mListener;

    public void setListener(TripDateSelectorListener listener) {
        mListener = listener;
    }

    @SuppressLint("NewApi")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date mDate = new Date(System.currentTimeMillis());

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_trip_date_select, null);
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.picker_date);
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        Button todayButton = (Button) v.findViewById(R.id.button_today);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.dialog_pick_date))
                .setView(v)
                .setPositiveButton(getActivity().getString(R.string.done), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Calendar c = Calendar.getInstance();
                        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                        mListener.onFinish(c.getTime());
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.cancel), null)
                .create();
    }
}
