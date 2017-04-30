package hestia.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.util.ArrayList;

import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

/*
** This class handles the dialog which is opened if a Device has the 'slide' option.
** It loads all the slider activators, and sends the new state onRelease.
 */

public class SlideDialog extends Dialog implements android.view.View.OnClickListener{
    private Device d;
    private ArrayList<Activator> fields;
    private Context context;
    private BackendInteractor cic;

    public SlideDialog(Context a, ArrayList<Activator> fields, Device d) {
        super(a);
        this.context = a;
        this.cic = BackendInteractor.getInstance();
        this.fields = fields;
        this.d = d;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slide_dialog);

        int count = 0;
        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);

        for (Activator activator : fields) {
            LinearLayout ll = new LinearLayout(context);

            TextView name = new TextView(context);
            name.setText(activator.getName());
            ll.addView(name);

            Float currState = Float.parseFloat(activator.getState().toString());
            SeekBar bar = createSeekBar(currState ,count, activator);
            ll.addView(bar);

            lm.addView(ll);
            count++;
        }
    }

    private SeekBar createSeekBar(float progress, int count, Activator a){
        final Activator act = a;
        SeekBar bar = new SeekBar(context);
        final int max_int = Integer.MAX_VALUE;
        bar.setMax(max_int);
        bar.setProgress((int)(progress* max_int));
        bar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80));
        bar.setId(count);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float value = (float)seekBar.getProgress()/max_int;
                ActivatorState<Float> state = act.getState();
                state.setRawState(value);
                cic.setActivatorState(d,act.getId(),state);
            }
        });
        return bar;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.confirm_button:
                Toast.makeText(getContext(), "Leaving", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.back_button:
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private LinearLayout generateButtons(){
        LinearLayout ll = new LinearLayout(context);
        final Button confirm = new Button(context);
        final Button cancel = new Button(context);
        confirm.setId(R.id.confirm_button);
        cancel.setId(R.id.back_button);
        confirm.setText("Confirm");
        cancel.setText("Cancel");
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ll.addView(confirm);
        ll.addView(cancel);
        return ll;
    }
}