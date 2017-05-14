package hestia.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import hestia.backend.BackendInteractor;

/**
 * The activity which presents a list containing all peripherals to the user. An activity is a
 * single, focused thing the user can do. Also setting the IP is added for improved persistency.
 */

public class DeviceListActivity extends SingleFragmentActivity {
    private static final String HESTIA_IP = "HESTIA.IP";
    private static final String SERVER_IP = "IP_OF_SERVER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        BackendInteractor backendInteractor = BackendInteractor.getInstance();

        SharedPreferences prefs = getSharedPreferences("HESTIA.IP", 0);
//        backendInteractor.setIp(prefs.getString("IP_OF_SERVER", backendInteractor.getIp()));
        Toast.makeText(this,"IP Address set to: " + backendInteractor.getIp() + ":"
                + backendInteractor.getPort(),Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
    }

    /**
     * When the app resumes, the list of devices is refreshed automatically using onResume.
     */
    @Override
    public void onResume(){
        BackendInteractor.getInstance().updateDevices();
        super.onResume();
    }

    @Override
    protected Fragment createFragment() {
        return new DeviceListFragment();
    }

    @Override
    protected void onStop() {
        storeIP();
        finish();
        //System.exit(0);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        storeIP();
        super.onDestroy();
    }

    private void storeIP(){
        BackendInteractor backendInteractor = BackendInteractor.getInstance();
        SharedPreferences.Editor prefs = getSharedPreferences(HESTIA_IP, 0).edit();
        prefs.putString(SERVER_IP, backendInteractor.getIp()).apply();
    }
}
