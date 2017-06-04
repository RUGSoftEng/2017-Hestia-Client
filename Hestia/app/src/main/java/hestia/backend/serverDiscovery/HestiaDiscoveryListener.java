package hestia.backend.serverDiscovery;

import android.content.res.Resources;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.rugged.application.hestia.R;

/**
 * The HestiaDiscoveryListener handles the events of finding a hestiaServer on the local network.
 * In most cases, a simple error message is logged currently. When a service is found the
 * resolveService method is called, which uses the HestiaResolveListener.
 * TODO link to the exception handling system.
 */
public class HestiaDiscoveryListener implements NsdManager.DiscoveryListener {
    private static final String SERVICE_NAME = Resources.getSystem().getString(R.string.ServiceName);
    private static final String SERVICE_TYPE = Resources.getSystem().getString(R.string.ServiceType);
    private final HestiaResolveListener resolveListener;
    private final NsdManager nsdManager;
    private String TAG = "HestiaDListener";

    public HestiaDiscoveryListener(HestiaResolveListener resolveListener, NsdManager nsdManager){
        this.resolveListener = resolveListener;
        this.nsdManager = nsdManager;
    }

    //  Called as soon as service discovery begins.
    @Override
    public void onDiscoveryStarted(String regType) {
        Log.d(TAG, "Service discovery started");
    }

    @Override
    public void onServiceFound(NsdServiceInfo service) {
        // A service was found!  Do something with it.
        Log.d(TAG, "Service discovery success" + service);
        if (!service.getServiceType().equals(SERVICE_TYPE)) {

            Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
        } else if (service.getServiceName().equals(SERVICE_NAME)) {

            Log.d(TAG, "Same machine: " + SERVICE_NAME);
        } else if (service.getServiceName().contains("Hestia")) {
            nsdManager.resolveService(service, resolveListener);
        }
    }

    @Override
    public void onServiceLost(NsdServiceInfo service) {
        // When the network service is no longer available.
        // Internal bookkeeping code goes here.
        Log.e(TAG, "service lost" + service);
    }

    @Override
    public void onDiscoveryStopped(String serviceType) {
        Log.i(TAG, "Discovery stopped: " + serviceType);
    }

    @Override
    public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        Log.e(TAG, "Discovery failed: Error code:" + errorCode);
        nsdManager.stopServiceDiscovery(this);
    }

    @Override
    public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        Log.e(TAG, "Discovery failed: Error code:" + errorCode);
        nsdManager.stopServiceDiscovery(this);
    }
}
