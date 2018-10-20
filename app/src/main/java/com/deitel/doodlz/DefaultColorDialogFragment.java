package com.deitel.doodlz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class DefaultColorDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View defaultColorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.default_background_color, null);
        builder.setView(defaultColorDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setMessage("change to default background color?");

        return builder.create(); // return dialog
    }
}
