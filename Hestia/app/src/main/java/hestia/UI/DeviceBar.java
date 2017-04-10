package hestia.UI;


import android.util.Log;
import android.view.View;
import android.widget.Switch;

import hestia.UIWidgets.HestiaSwitch;
import hestia.backend.Device;

public class DeviceBar {
    //TODO Add ImageView
    private Device d;
    private HestiaSwitch hestiaSwitch;

    private final static String TAG = "DeviceBar";

    public DeviceBar(Device d, HestiaSwitch hestiaSwitch) {
        this.d = d;
        this.hestiaSwitch = hestiaSwitch;
    }

    public Device getDevice() {
        return d;
    }

    public void setDevice(Device d) {
        this.d = d;
    }

    public HestiaSwitch getHestiaSwitch() {
        return hestiaSwitch;
    }

    public void setHestiaSwitch(HestiaSwitch hestiaSwitch) {
        this.hestiaSwitch = hestiaSwitch;
    }

    public void setLayout(View v, int layoutId, boolean state) {
        hestiaSwitch.getActivatorSwitch().setChecked(state);
        Log.i(TAG, "Layout changed for: " + d.getName() + " And switch truth is: " +
                hestiaSwitch.getActivatorSwitch().isChecked());
        hestiaSwitch.addLayout(v, layoutId);

    }

    @Override
    public boolean equals(Object object)
    {
        boolean equal = false;

        if (object != null && object instanceof DeviceBar)
        {
            if(this.d.getDeviceId() == ((DeviceBar) object).getDevice().getDeviceId()){
                equal = true;
            }
        }
        return equal;
    }

}
