/*
This class is the host of the PeripheralListFragment.
 */

package hestia.UI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import hestia.backend.ClientInteractionController;
import hestia.backend.Device;

/**
 * The activity which presents a list containing all peripherals to the user. An activity is a
 * single, focused thing the user can do.
 */
public class DeviceListActivity extends SingleFragmentActivity {
    private final static String TAG = "DeviceListActivity";
    @Override
    protected Fragment createFragment() {
        return new DeviceListFragment();
    }
}
