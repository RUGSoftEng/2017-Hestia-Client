package hestia.UIWidgets;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

public class HestiaSwitch implements UIWidget, CompoundButton.OnCheckedChangeListener {
    private final static String TAG = "HestiaSwitch";
    private Device device;
    private Activator activator;
    private Switch activatorSwitch;
    private BackendInteractor backendInteractor;

    public HestiaSwitch(Device device, Activator activator, Context context) {
        this.activator = activator;
        activatorSwitch = new Switch(context);
        activatorSwitch.setChecked(Boolean.valueOf(activator.getState().getRawState().toString()));
        this.device = device;
        this.backendInteractor = BackendInteractor.getInstance();
        Log.i(TAG, "HestiaSwitch created");
    }
/*
    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public void setActivator(Activator activator) {
        this.activator = activator;
    }

    @Override
    public Activator getActivator() {
        return activator;
    }
*/
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
        activatorSwitch.setOnCheckedChangeListener(null);
        activatorSwitch.setOnCheckedChangeListener(this);
    }
}
