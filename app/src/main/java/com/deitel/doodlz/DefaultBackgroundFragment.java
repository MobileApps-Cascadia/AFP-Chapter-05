package com.deitel.doodlz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by deb on 10/11/16.
 */

public class DefaultBackgroundFragment extends ColorDialogFragment {
    public DefaultBackgroundFragment() {} // required for factory method

    public DefaultBackgroundFragment newInstance() {
        DefaultBackgroundFragment fragment = new DefaultBackgroundFragment();
        return fragment;
    }

    // get an AlertDialog from the super class, update the title, and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog dialog = (AlertDialog) super.onCreateDialog(bundle);

        dialog.setTitle(R.string.title_def_bgd_dialog);
        setColor(getDoodleView().getDefaultBackgroundColor());
        getColorView().setBackgroundColor(getColor());

        // modify Set Color Button
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.button_set_color),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDoodleView().setDefaultBackgroundColor(getColor());
                    }
                }
        );

        return dialog;
    }
}
