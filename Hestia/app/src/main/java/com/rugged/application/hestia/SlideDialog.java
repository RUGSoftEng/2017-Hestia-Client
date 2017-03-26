/*
This class handles the dialog which is opened if a Device has the 'slide' option
 */

package com.rugged.application.hestia;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Random;

public class SlideDialog extends Dialog {
    private final static String TAG = "SlideDialog";
    private Device d;
    private boolean changer;

    public SlideDialog(Context context, Device d) {
        super(context);
        setContentView(R.layout.slide_dialog);
        setTitle("Change your slide");
        this.d = d;
        changer = true;

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_dialog);
        int id = -1;

        for (int i = 0; i < d.getActivators().size(); i++) {
            if (d.getActivators().get(i).getType().equals("TOGGLE")) {
                id = d.getActivators().get(i).getId();
            }
        }

        final ActivatorSwitch switchButton = new ActivatorSwitch(new Random().nextInt(4),
                this.findViewById(R.id.slide_dialog), R.id.switch_dialog);


        for (int i = 0; i < d.getActivators().size(); i++) {
            if (d.getActivators().get(i).getType().equals("SLIDER")) {
                id = d.getActivators().get(i).getId();
            }
        }


        final ActivatorSeekbar seekBar = new ActivatorSeekbar(id,
                findViewById(R.id.slide_dialog), R.id.slide_seek_bar);
        seekBar.getActivatorSeekBar().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar s, int i, boolean b) {
                layout.setBackgroundColor(0xffffffff - s.getProgress());
                Toast.makeText(getContext(), Integer.toString(seekBar.getActivatorId()),
                        Toast.LENGTH_SHORT).show();
                if (s.getProgress() > 0) {
                    setChange(false);
                    switchButton.getActivatorSwitch().setChecked(true);

                    //handle coloring here, so do not call
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
                    if(getChange()) {
                        seekBar.getActivatorSeekBar().setProgress(seekBar.getActivatorSeekBar().
                                getMax());
                        setChange(true);
                    }
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

    private void setChange(boolean change) {
        this.changer = change;
    }

    private boolean getChange() {
        return changer;
    }
}
