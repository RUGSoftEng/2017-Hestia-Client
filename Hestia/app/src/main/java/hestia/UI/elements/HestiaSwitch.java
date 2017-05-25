package hestia.UI.elements;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;

import hestia.backend.models.Activator;
import hestia.backend.models.ActivatorState;

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
    public void onCheckedChanged(CompoundButton compoundButton, boolean currentState) {
        ActivatorState state = activator.getState();
        state.setRawState(currentState);

        // TODO: Handle try-catch properly

        try {
            activator.setState(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Sending a post to the server");
    }

    public void addLayout(View v, int layoutId) {
        activatorSwitch = (Switch)v.findViewById(layoutId);
        activatorSwitch.setOnCheckedChangeListener(this);
    }
}
