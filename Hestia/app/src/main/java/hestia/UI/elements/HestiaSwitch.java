package hestia.UI.elements;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.io.IOException;
import java.util.ArrayList;

import hestia.backend.ComFaultException;
import hestia.backend.models.Activator;
import hestia.backend.models.ActivatorState;
import hestia.backend.models.Device;

public class HestiaSwitch implements CompoundButton.OnCheckedChangeListener {
    private final static String TAG = "HestiaSwitch";
    private Activator activator;
    private Switch activatorSwitch;

    public HestiaSwitch(Activator activator, Context context) {
        this.activator = activator;
        activatorSwitch = new Switch(context);
        activatorSwitch.setChecked(Boolean.valueOf(activator.getState().getRawState().toString()));
        Log.i(TAG, "HestiaSwitch created, TOGGLE=" + activatorSwitch.isChecked());
    }

    public Switch getActivatorSwitch() {
        return activatorSwitch;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, final boolean currentState) {
//        ActivatorState state = activator.getState();
//        state.setRawState(currentState);
//
//
//        Log.d(TAG, "Changed the switch to " + currentState);
//
//        try {
//            activator.setState(state);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ComFaultException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, "Sending a post to the server");

        final ActivatorState state = activator.getState();
        state.setRawState(currentState);
        new AsyncTask<Object, Object, Integer>() {
            @Override
            protected Integer  doInBackground(Object... params) {
                Log.d(TAG, "Changed the switch to " + currentState);

                try {
                    activator.setState(state);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ComFaultException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Sending a post to the server");

                return 0;
            }

            @Override
            protected void onPostExecute(Integer result) {
                // Update GUI
            }
        }.execute();


    }

    public void addLayout(View view, int layoutId) {
        activatorSwitch = (Switch)view.findViewById(layoutId);
        activatorSwitch.setOnCheckedChangeListener(this);
    }
}
