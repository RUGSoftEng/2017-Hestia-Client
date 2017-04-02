package hestia.UIWidgets;

import android.view.View;
import android.widget.Switch;

import hestia.backend.Activator;
import hestia.backend.Device;

public class HestiaSwitch implements UIWidget {
    private final static String TAG = "HestiaSwitch";
    private Activator a;
    private Switch activatorSwitch;
    private Device d;

    public HestiaSwitch(Device d, Activator a, View v, int layoutId) {
        this.a = a;
        activatorSwitch = (Switch) v.findViewById(layoutId);
        this.d = d;
    }

    @Override
    public Device getDevice() {
        return d;
    }

    @Override
    public void setActivatorId(Activator a) {
        this.a = a;
    }

    @Override
    public Activator getActivatorId() {
        return a;
    }

    public Switch getActivatorSwitch() {
        return activatorSwitch;
    }
}
