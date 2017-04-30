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
    private Device d;
    private Activator a;
    private Switch activatorSwitch;
    private BackendInteractor cic;
    private boolean check;

    public HestiaSwitch(Device d, Activator a, Context c) {
        this.a = a;
        Log.i(TAG, "HestiaSwitch created");
        activatorSwitch = new Switch(c);
        setCheck(Boolean.parseBoolean(a.getState().toString()));
        this.d = d;
        this.cic = BackendInteractor.getInstance();
    }

    @Override
    public Device getDevice() {
        return d;
    }

    @Override
    public void setActivator(Activator a) {
        this.a = a;
    }

    @Override
    public Activator getActivator() {
        return a;
    }

    public Switch getActivatorSwitch() {
        return activatorSwitch;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int activatorId = a.getId();

        ActivatorState state = a.getState();
        if (b) {
            state.setRawState(true);
            cic.setActivatorState(d, activatorId, state);
            setCheck(true);
            Log.i(TAG, d.getName() + " set to true");
        } else {
            state.setRawState(false);
            setCheck(false);
            cic.setActivatorState(d, activatorId, state);
            Log.i(TAG, d.getName() + " set to false");
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
