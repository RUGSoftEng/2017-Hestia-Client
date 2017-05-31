package hestia.backend.models;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkDiscovery {

    private InetAddress host;

    private void networkServerDiscovery() {
        new AsyncTask<Void, Void, InetAddress>() {
            private NsdServiceInfo mService;
            private NsdManager.ResolveListener mResolveListener;
            private NsdManager mNsdManager;
            String SERVICE_NAME = "HestiaServer";
            String SERVICE_TYPE = "_http._tcp.";

            private String TAG = "NetDiscovery";

            @Override
            protected InetAddress doInBackground(Void... params) {
                NsdManager.DiscoveryListener discoveryListener = new NsdManager.DiscoveryListener() {
                    private String TAG = "DiscoveryService";

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
                            // Service type is the string containing the protocol and
                            // transport layer for this service.
                            Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                        } else if (service.getServiceName().equals(SERVICE_NAME)) {
                            // The name of the service tells the user what they'd be
                            // connecting to. It could be "Bob's Chat App".
                            Log.d(TAG, "Same machine: " + SERVICE_NAME);
                        } else if (service.getServiceName().contains("NsdChat")) {
                            mNsdManager.resolveService(service, mResolveListener);
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
                        mNsdManager.stopServiceDiscovery(this);
                    }

                    @Override
                    public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                        Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                        mNsdManager.stopServiceDiscovery(this);
                    }

                };

                mResolveListener = new NsdManager.ResolveListener() {
                    @Override
                    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                        // Called when the resolve fails.  Use the error code to debug.
                        Log.e(TAG, "Resolve failed" + errorCode);
                    }

                    @Override
                    public void onServiceResolved(NsdServiceInfo serviceInfo) {
                        Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                        if (serviceInfo.getServiceName().equals(SERVICE_NAME)) {
                            Log.d(TAG, "Same IP.");
                            return;
                        }
                        mService = serviceInfo;
                        host = mService.getHost();
                    }
                };

                mNsdManager.discoverServices("_http._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);
                return host;
            }
        };
    }

}
