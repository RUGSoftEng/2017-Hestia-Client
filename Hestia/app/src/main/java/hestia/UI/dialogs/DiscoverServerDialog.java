package hestia.UI.dialogs;

import android.content.Context;
import android.content.res.Configuration;
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
import hestia.backend.serverDiscovery.NsdHelper;

/**
 * This class represents the dialog screen with which the IP-address of the server is asked from the
 * user.
 */

public class DiscoverServerDialog extends HestiaDialog {
    private final static String TAG = "DiscoverServerDialog";
    private String foundIP;

    private EditText ipField;
    private Button discoverButton;

    private NetworkHandler handler;

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
        new AsyncTask<Object, Object, NsdServiceInfo>() {
            @Override
            protected NsdServiceInfo doInBackground(Object... params) {
                NsdManager nsdManager = (NsdManager) getContext().getSystemService(Context.NSD_SERVICE);
                String serviceName = getResources().getString(R.string.serviceName);
                String serviceType = getResources().getString(R.string.serviceType);
                NsdHelper nsdHelper = new NsdHelper(nsdManager, serviceName, serviceType);
//                nsdHelper.registerService(R.string.default_port);
                nsdHelper.discoverServices();
                NsdServiceInfo serviceInfo = nsdHelper.getServiceInfo();
                return serviceInfo;
            }

            @Override
            protected void onPostExecute(NsdServiceInfo serviceInfo) {
                if(serviceInfo != null) {
                    Log.d(TAG, "ServiceInfo is NOT null");
                    String ip = serviceInfo.getHost().getHostAddress();
                    Integer port = serviceInfo.getPort();
                    discoverButton.setText("Server found at: "+ip);
                    foundIP = ip;
                    //nsdHelper.tearDown();
                } else {
                    Log.d(TAG, "ServiceInfo is null");
                    discoverButton.setText("Server not found");
                }
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
            public void onClick(View v) {
                ipField.setText(foundIP);
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
    }
}
