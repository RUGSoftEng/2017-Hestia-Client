/*
This class is the host of the PeripheralListFragment.
 */

package hestia.UI;

import android.support.v4.app.Fragment;

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
