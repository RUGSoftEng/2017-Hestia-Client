/*
This class is the host of the PeripheralListFragment.
 */

package hestia.UI;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
    public void onCreate(Bundle savedInstanceState) {
        ClientInteractionController cic = ClientInteractionController.getInstance();
        SharedPreferences prefs = getSharedPreferences("HESTIA.IP", 0);
        cic.setIp(prefs.getString("IP_OF_SERVER", cic.getIp()));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new DeviceListFragment();
    }

    @Override
    protected void onStop() {
        storeIP();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        storeIP();
        super.onDestroy();
    }

    private void storeIP(){
        ClientInteractionController cic = ClientInteractionController.getInstance();
        SharedPreferences.Editor prefs = getSharedPreferences("HESTIA.IP", 0).edit();
        prefs.putString("IP_OF_SERVER", cic.getIp()).apply();
    }
}
