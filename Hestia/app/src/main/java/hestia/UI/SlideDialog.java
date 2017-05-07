package hestia.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

/**
 * This class handles the dialog which is opened if a Device has the 'slide' option.
 * It loads all the slider activators, and sends the new state onRelease.
 */

public class SlideDialog extends Dialog implements android.view.View.OnClickListener{
    private Device device;
    private ArrayList<Activator> fields;
    private Context context;
    private BackendInteractor backendInteractor;

    public SlideDialog(Context context, Device device) {
        super(context);
        this.context = context;
        this.backendInteractor = BackendInteractor.getInstance();
        this.device = device;
        this.fields = device.getSliders();
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

            Float currState = Float.parseFloat(activator.getState().getRawState().toString());
            SeekBar bar = createSeekBar(currState ,count, activator);
            ll.addView(bar);

            lm.addView(ll);
            count++;
        }
    }

    private SeekBar createSeekBar(float progress, int count, Activator activator){
        final Activator act = activator;
        SeekBar bar = new SeekBar(context);
        //final int maxInt = Integer.MAX_VALUE;
        final int maxInt = 100;
        bar.setMax(maxInt);
        bar.setProgress((int)(progress * maxInt));
        final String TAG = "SlideDialog";
        Log.d(TAG, "------------- Before changing progress ---------" );
        Log.d(TAG, "MAX_INT="+maxInt);
        Log.d(TAG, "Initial progress="+progress);
        Log.d(TAG, "progress set (progress*maxInt)="+progress*maxInt);
        Log.d(TAG, "---------------------------------------------" );
        bar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80));
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
                float value = (float)seekBar.getProgress()/maxInt;
                ActivatorState<Float> state = act.getState();
                state.setRawState(value);
                Log.d(TAG, "------------- After changing progress ---------" );
                Log.d(TAG, "new value="+value);
                Log.d(TAG, "---------------------------------------------" );
                backendInteractor.setActivatorState(device,act,state);
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