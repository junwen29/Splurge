package com.is3261.splurge.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.is3261.splurge.R;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class AlertDialogFactory {

    public static AlertDialog unsaved(Context context, DialogInterface.OnClickListener okListener) {
        return new AlertDialog.Builder(context).setTitle(R.string.dialog_discard)
                .setMessage(R.string.dialog_unsaved)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
