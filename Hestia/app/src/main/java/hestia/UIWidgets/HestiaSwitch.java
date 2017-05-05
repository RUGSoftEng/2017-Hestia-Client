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
    private boolean check;

    public HestiaSwitch(Device device, Activator activator, Context c) {
        this.activator = activator;
        Log.i(TAG, "HestiaSwitch created");
        activatorSwitch = new Switch(c);
        setCheck(Boolean.parseBoolean(activator.getState().toString()));
        this.device = device;
        this.backendInteractor = BackendInteractor.getInstance();
    }

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

    public Switch getActivatorSwitch() {
        return activatorSwitch;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
        //int activatorId = a.getId();

        ActivatorState state = activator.getState();
        if (bool) {
            state.setRawState(true);
            backendInteractor.setActivatorState(device, activator, state);
            setCheck(true);
            Log.i(TAG, device.getName() + " set to true");
        } else {
            state.setRawState(false);
            setCheck(false);
            backendInteractor.setActivatorState(device, activator, state);
            Log.i(TAG, device.getName() + " set to false");
        }
    }

    public void addLayout(View v, int layoutId) {
        activatorSwitch = (Switch)v.findViewById(layoutId);
        activatorSwitch.setOnCheckedChangeListener(null);
        activatorSwitch.setChecked(check);
        activatorSwitch.setOnCheckedChangeListener(this);
    }

    private void setCheck(boolean b) {
        check = b;
    }

}
