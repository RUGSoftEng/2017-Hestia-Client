package hestia.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import hestia.backend.BackendInteractor;

/**
 * The activity which presents a list containing all peripherals to the user. An activity is a
 * single, focused thing the user can do. Also setting the IP is added for improved persistency.
 */

public class DeviceListActivity extends SingleFragmentActivity {
    private final static String TAG = "DeviceListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        BackendInteractor cic = BackendInteractor.getInstance();
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
        BackendInteractor cic = BackendInteractor.getInstance();
        SharedPreferences.Editor prefs = getSharedPreferences("HESTIA.IP", 0).edit();
        prefs.putString("IP_OF_SERVER", cic.getIp()).apply();
    }
}
