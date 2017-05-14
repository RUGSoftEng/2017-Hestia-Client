package hestia.UI;

import android.util.Log;
import android.view.View;
import android.widget.Switch;

import hestia.backend.Device;

/**
 *  This class takes care of the deviceBar.
 * The devicebar is the 'row' in the expandable list of a single device.
 * The DeviceBar class also sets the HestiaSwitch for the boolean activator.
 */

public class DeviceBar {
    private Device device;
    private HestiaSwitch hestiaSwitch;

    private final static String TAG = "DeviceBar";

    public DeviceBar(Device device, HestiaSwitch hestiaSwitch) {
        this.device = device;
        this.hestiaSwitch = hestiaSwitch;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setLayout(View view, int layoutId, boolean state) {
        hestiaSwitch.addLayout(view, layoutId);
        hestiaSwitch.getActivatorSwitch().setChecked(state);
        Log.i(TAG, "Layout changed for: " + device.getName() + " And switch truth is: " +
                hestiaSwitch.getActivatorSwitch().isChecked());
    }

    @Override
    public boolean equals(Object object) {
        boolean equal = false;

        if (object != null && object instanceof DeviceBar) {
            if(this.device.getId() == ((DeviceBar) object).getDevice().getId()){
                equal = true;
            }
        }
        return equal;
    }

}
