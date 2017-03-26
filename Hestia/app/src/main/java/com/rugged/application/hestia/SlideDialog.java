/*
This class handles the dialog which is opened if a Device has the 'slide' option
 */

package com.rugged.application.hestia;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;

public class SlideDialog extends Dialog {
    public SlideDialog(Context context) {
        super(context);
        setContentView(R.layout.slide_dialog);
        setTitle("Change your slide");
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_dialog);

        final Switch switchButton = (Switch) findViewById(R.id.switch_dialog);

        final SeekBar s = (SeekBar) findViewById(R.id.slide_seek_bar);
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                layout.setBackgroundColor(0xffffffff - seekBar.getProgress());
                if (seekBar.getProgress() > 0) {
                    switchButton.setChecked(true);
                }else {
                    switchButton.setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    s.setProgress(s.getMax());
                } else {
                    s.setProgress(0);
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
