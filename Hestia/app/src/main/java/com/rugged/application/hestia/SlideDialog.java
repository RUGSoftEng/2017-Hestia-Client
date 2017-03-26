/*
This class handles the dialog which is opened if a Device has the 'slide' option
 */

package com.rugged.application.hestia;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.Random;

public class SlideDialog extends Dialog {
    private final static String TAG = "SlideDialog";

    public SlideDialog(Context context, Device d) {
        super(context);
        setContentView(R.layout.slide_dialog);
        setTitle("Change your slide");

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_dialog);

        final ActivatorSwitch switchButton = new ActivatorSwitch(new Random().nextInt(4),
                this.findViewById(R.id.slide_dialog), R.id.switch_dialog);

//        final Switch switchButton = (Switch) findViewById(R.id.switch_dialog);

        final ActivatorSeekbar seekBar = new ActivatorSeekbar(new Random().nextInt(4),
                findViewById(R.id.slide_dialog), R.id.slide_seek_bar);
        seekBar.getActivatorSeekBar().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar s, int i, boolean b) {
                layout.setBackgroundColor(0xffffffff - s.getProgress());
                if (s.getProgress() > 0) {
                    switchButton.getActivatorSwitch().setChecked(true);
                }else {
                    switchButton.getActivatorSwitch().setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        switchButton.getActivatorSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.i(TAG, "Pressing the button");
                    seekBar.getActivatorSeekBar().setProgress(seekBar.getActivatorSeekBar().getMax());
                } else {
                    seekBar.getActivatorSeekBar().setProgress(0);
                }
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data to the server
                dismiss();
            }
        });
    }
}
