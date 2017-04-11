package hestia.UIWidgets;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.ClientInteractionController;
import hestia.backend.Device;

public class HestiaSwitch implements UIWidget, CompoundButton.OnCheckedChangeListener {
    private final static String TAG = "HestiaSwitch";
    private Device d;
    private Activator a;
    private Switch activatorSwitch;
    private ClientInteractionController cic;
    private boolean check;

    public HestiaSwitch(Device d, Activator a, Context c) {
        this.a = a;
//        activatorSwitch = createActivatorSwitch(c);
        Log.i(TAG, "HestiaSwitch created");
        activatorSwitch = new Switch(c);
        setCheck(Boolean.parseBoolean(a.getState().toString()));
        this.d = d;
        this.cic = ClientInteractionController.getInstance();
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

    public void setActivatorSwitch(Switch s) {
//        s.setOnCheckedChangeListener(null);
//        s.setChecked(activatorSwitch.isChecked());
        s.setOnCheckedChangeListener(this);
        this.activatorSwitch = s;
    }

//    private Switch createActivatorSwitch(Context c) {
//        Switch s = new Switch(c);
//        Log.i(TAG, "the switch is being created");
//
//        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//        return s;
//    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int activatorId = a.getId();

        ActivatorState state = a.getState();
//        activatorSwitch.setChecked(b);
        if (b) {
            // True
            state.setState(true);
            cic.setActivatorState(d, activatorId, state);
            setCheck(true);
            Log.i(TAG, d.getName() + " set to true");
        } else {
            // False
            state.setState(false);
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
