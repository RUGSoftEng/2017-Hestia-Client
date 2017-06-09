package hestia.UI.dialogs;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import hestia.UI.HestiaApplication;
import hestia.backend.NetworkHandler;

/**
 * This class represents the dialog screen with which the IP-address of the server is asked from the
 * user.
 */

public class DiscoverServerDialog extends HestiaDialog {
    private final static String TAG = "DiscoverServerDialog";
    private String foundIp;
    private EditText ipField;
    private Button discoverButton;
    private NetworkHandler handler;
    private NsdManager.ResolveListener resolveListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager nsdManager;
    private final String serviceName = HestiaApplication.getContext().getString(R.string.serviceName);
    private final String serviceType = HestiaApplication.getContext().getString(R.string.serviceType);

    public static DiscoverServerDialog newInstance() {
        DiscoverServerDialog fragment = new DiscoverServerDialog();
        return fragment;
    }

    public void setNetworkHandler(NetworkHandler handler) {
        this.handler = handler;
    }

    @Override
    String buildTitle() {
        return "Find server";
    }

    @Override
    View buildView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.ip_dialog, null);
        initDiscoverButton(view);
        initIpField(view);

        discoverServer();

        return view;
    }

    private void discoverServer() {
        new AsyncTask<Object, Object, Void>() {
            @Override
            protected Void doInBackground(Object... params) {
                nsdManager = (NsdManager) getContext().getSystemService(Context.NSD_SERVICE);
                initializeDiscoveryListener();
                initializeResolveListener();
                //NsdHelper nsdHelper = new NsdHelper(nsdManager, serviceName, serviceType);
                //nsdHelper.discoverServices();
                //NsdServiceInfo serviceInfo = nsdHelper.getServiceInfo();
                nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
                return null;
            }
        }.execute();
    }

    private void initIpField(View view) {
        ipField = (EditText) view.findViewById(R.id.ip);
        ipField.setRawInputType(Configuration.KEYBOARD_12KEY);

        String currentIP = handler.getIp();
        if (currentIP == null) {
            ipField.setHint("Enter ip here");
        } else {
            ipField.setText(currentIP);
            ipField.setSelection(ipField.getText().length());
        }
    }

    private void initDiscoverButton(View view) {
        discoverButton = (Button) view.findViewById(R.id.discover);
        discoverButton.setText("Searching...");
        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipField.setText(foundIp);
                ipField.setSelection(ipField.getText().length());
            }
        });
        discoverButton.setEnabled(false);
    }


    @Override
    void pressConfirm() {
        String enteredIp = ipField.getText().toString();
        if(enteredIp!=null) {
            handler.setIp(enteredIp);
            Log.i(TAG, "My ip is changed to: " + enteredIp);
            Toast.makeText(getContext(), handler.getIp(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    void pressCancel() {
        Toast.makeText(getContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
        nsdManager.stopServiceDiscovery(discoveryListener);
    }


    // -----------------------

    private void initializeDiscoveryListener() {
        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
                Log.d(TAG, "  Service name: " + serviceName);
                Log.d(TAG, "  Service type: " + serviceType);
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                if (!service.getServiceType().equals(serviceType)) {
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    Log.d(TAG, "Same machine: " + serviceName);
                } else {
                    Log.d(TAG, "Found a different Host: " + serviceName);
                    nsdManager.resolveService(service, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost" + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.d(TAG, "Discovery stopped: " + serviceType);
                discoverButton.setText("Server Not Found");
//                discoverButton.setBackgroundColor(Color.RED);
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
        };
    }

    private void initializeResolveListener() {
        resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.d(TAG, "Resolve Succeeded. " + serviceInfo);
                if (serviceInfo.getServiceName().equals(serviceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                String ip = serviceInfo.getHost().getHostAddress();
                foundIp = ip;
                discoverButton.setText("Server Found: " + ip);
                discoverButton.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed" + errorCode);
            }
        };
    }
}
