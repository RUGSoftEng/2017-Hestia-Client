/*
** This class handles the dialog which is opened if a Device has the 'slide' option.
** It loads all the slider activators, and sends the new state onRelease.
 */

package hestia.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
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
import hestia.backend.ClientInteractionController;
import hestia.backend.Device;

public class SlideDialog extends Dialog implements android.view.View.OnClickListener{
    private final static String TAG = "SlideDialog";
    private Device d;
    private ArrayList<Activator> fields;
    private Context context;
    private ClientInteractionController cic;

    public SlideDialog(Context a, ArrayList<Activator> fields, Device d) {
        super(a);
        this.context = a;
        this.cic = ClientInteractionController.getInstance();
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

            // Add text
            TextView name = new TextView(context);
            name.setText(activator.getName());
            ll.addView(name);

            //Add field
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
                state.setState(value);
                cic.setActivatorState(d,act.getId(),state);
            }
        });
        return bar;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case 11:
                Toast.makeText(getContext(), "Leaving", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case 12:
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


    private LinearLayout generateButtons(){
        // Add buttons.
        LinearLayout ll = new LinearLayout(context);
        int i = 10;
        final Button confirm = new Button(context);
        final Button cancel = new Button(context);
        confirm.setId(i + 1);
        cancel.setId(i + 2);
        confirm.setText("Confirm");
        cancel.setText("Cancel");
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ll.addView(confirm);
        ll.addView(cancel);
        return ll;
    }
}