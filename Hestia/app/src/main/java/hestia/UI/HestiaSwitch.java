package hestia.UI;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

public class HestiaSwitch implements CompoundButton.OnCheckedChangeListener {
    private final static String TAG = "HestiaSwitch";
    private Device device;
    private Activator activator;
    private Switch activatorSwitch;
    private BackendInteractor backendInteractor = BackendInteractor.getInstance();;

    public HestiaSwitch(Device device, Activator activator, Context context) {
        this.device = device;
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
        backendInteractor.setActivatorState(device, activator, state);
    }

    public void addLayout(View v, int layoutId) {
        activatorSwitch = (Switch)v.findViewById(layoutId);
        activatorSwitch.setOnCheckedChangeListener(this);
    }
}
