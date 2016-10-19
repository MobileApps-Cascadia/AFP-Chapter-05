package com.deitel.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class BackgroundColorFragment extends DialogFragment {
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View backcolorView;
    private int backgroundColor;


    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // create dialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_background_color, null);
        builder.setView(colorDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setTitle(R.string.title_backcolor_dialog);

        // get the color SeekBars and set their onChange listeners
        alphaSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.alphaSeekBar);
        redSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.redSeekBar);
        greenSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.greenSeekBar);
        blueSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.blueSeekBar);
        backcolorView = colorDialogView.findViewById(R.id.backcolorView);

        // register SeekBar event listeners
        alphaSeekBar.setOnSeekBarChangeListener(backcolorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(backcolorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(backcolorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(backcolorChangedListener);

        // use current drawing color to set SeekBar values
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        backgroundColor = doodleView.getBackgroundColor();
        alphaSeekBar.setProgress(Color.alpha(backgroundColor));
        redSeekBar.setProgress(Color.red(backgroundColor));
        greenSeekBar.setProgress(Color.green(backgroundColor));
        blueSeekBar.setProgress(Color.blue(backgroundColor));

        // add Set Color Button
        builder.setPositiveButton(R.string.button_set_color,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doodleView.setBackgroundColor(backgroundColor);
                    }
                }
        );
        return builder.create(); // return dialog
    }

    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.doodleFragment);
    }

    // tell MainActivityFragment that dialog is now displayed
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    // tell MainActivityFragment that dialog is no longer displayed
    @Override
    public void onDetach() {
        super.onDetach();
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }

    private final SeekBar.OnSeekBarChangeListener backcolorChangedListener =
            new SeekBar.OnSeekBarChangeListener() {
                // display the updated color
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {

                    if (fromUser) // user, not program, changed SeekBar progress
                        backgroundColor = Color.argb(alphaSeekBar.getProgress(),
                                redSeekBar.getProgress(), greenSeekBar.getProgress(),
                                blueSeekBar.getProgress());
                    backcolorView.setBackgroundColor(backgroundColor);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {} // required

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {} // required
            };
}
