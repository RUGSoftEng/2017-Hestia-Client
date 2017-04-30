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
        BackendInteractor backendInteractor = BackendInteractor.getInstance();
        SharedPreferences prefs = getSharedPreferences("HESTIA.IP", 0);
        backendInteractor.setIp(prefs.getString("IP_OF_SERVER", backendInteractor.getIp()));
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
        BackendInteractor backendInteractor = BackendInteractor.getInstance();
        SharedPreferences.Editor prefs = getSharedPreferences("HESTIA.IP", 0).edit();
        prefs.putString("IP_OF_SERVER", backendInteractor.getIp()).apply();
    }
}
