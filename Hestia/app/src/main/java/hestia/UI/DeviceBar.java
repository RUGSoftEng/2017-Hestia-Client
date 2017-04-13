package hestia.UI;

import android.util.Log;
import android.view.View;

import hestia.UIWidgets.HestiaSwitch;
import hestia.backend.Device;

/**
 *  This class takes care of the deviceBar.
 * The devicebar is the 'row' in the expandable list of a single device.
 * The DeviceBar class also sets the HestiaSwitch for the boolean activator.
 */

public class DeviceBar {
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

    public void setLayout(View v, int layoutId, boolean state) {
        hestiaSwitch.getActivatorSwitch().setChecked(state);
        Log.i(TAG, "Layout changed for: " + d.getName() + " And switch truth is: " +
                hestiaSwitch.getActivatorSwitch().isChecked());
        hestiaSwitch.addLayout(v, layoutId);
    }

    @Override
    public boolean equals(Object object) {
        boolean equal = false;

        if (object != null && object instanceof DeviceBar) {
            if(this.d.getDeviceId() == ((DeviceBar) object).getDevice().getDeviceId()){
                equal = true;
            }
        }
        return equal;
    }

}
