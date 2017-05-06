package hestia.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
    private Device device;
    private ArrayList<Activator> fields;
    private Context context;
    private BackendInteractor backendInteractor;

    public SlideDialog(Context context, ArrayList<Activator> fields, Device device) {
        super(context);
        this.context = context;
        this.backendInteractor = BackendInteractor.getInstance();
        this.fields = fields;
        this.device = device;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slide_dialog);

        int count = 0;
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearMain);

        for (Activator activator : fields) {
            LinearLayout subLayout = new LinearLayout(context);

            TextView name = new TextView(context);
            name.setText(activator.getName());
            subLayout.addView(name);

            Float currState = Float.parseFloat(activator.getState().toString());
            SeekBar bar = createSeekBar(currState ,count, activator);
            subLayout.addView(bar);

            mainLayout.addView(subLayout);
            count++;
        }
    }

    private SeekBar createSeekBar(float progress, int count, Activator activator){
        final Activator act = activator;
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
                backendInteractor.setActivatorState(device,act,state);
            }
        });
        return bar;
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}