package com.deitel.doodlz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by deb on 10/11/16.
 */

public class BackgroundFragment extends ColorDialogFragment {

    public BackgroundFragment() {} // required for factory method

    public BackgroundFragment newInstance() {
        BackgroundFragment fragment = new BackgroundFragment();
        return fragment;
    }

    // get an AlertDialog from the super class, update the title, and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog dialog = (AlertDialog) super.onCreateDialog(bundle);

        dialog.setTitle(R.string.title_background_dialog);
        // set color to background color
        setColor(getDoodleView().getBackgroundColor());
        getColorView().setBackgroundColor(getColor());

        // modify Set Color Button
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.button_set_color),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDoodleView().setBackgroundColor(getColor());
                    }
                }
        );

        return dialog;
    }
}
