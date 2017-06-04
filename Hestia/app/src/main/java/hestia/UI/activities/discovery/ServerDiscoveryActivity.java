package hestia.UI.activities.discovery;

import android.app.Activity;
import android.content.res.Resources;
import android.net.nsd.NsdManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.rugged.application.hestia.R;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.serverDiscovery.HestiaDiscoveryListener;
import hestia.backend.serverDiscovery.HestiaResolveListener;

public class ServerDiscoveryActivity extends Activity {
    private Button findServerButton;
    private EditText ipField;
    private ServerCollectionsInteractor serverCollectionsInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        findServerButton = (Button) findViewById(R.id.findServerButton);
        ipField = (EditText) findViewById(R.id.ipField);

        findServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performNetworkDiscovery();
            }
        });
    }

    /**
     * This method uses the ZeroConf system to look for Hestia servers on the local network.
     * If it finds them it will replace the current ServerCollectionsInteractor with a new one
     * using the newly found IP-address and port.
     * TODO change control flow so login screen is shown before connecting.
     *
     * @see hestia.backend.serverDiscovery.HestiaDiscoveryListener
     * @see hestia.backend.serverDiscovery.HestiaResolveListener
     */
    public void performNetworkDiscovery() {
        new AsyncTask<Void, Void, Boolean>() {
            private final String SERVICE_TYPE = Resources.getSystem().getString(R.string.ServiceType);
            private HestiaResolveListener resolveListener;
            private NsdManager hestiaNsdManager;
            private String TAG = "ServerDiscoveryActivity";

            @Override
            protected Boolean doInBackground(Void... params) {
                Boolean isSuccessful = false;
                resolveListener = new HestiaResolveListener(serverCollectionsInteractor);
                HestiaDiscoveryListener discoveryListener = new HestiaDiscoveryListener(resolveListener, hestiaNsdManager);

                hestiaNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                if(isSuccessful) {
                    // TODO: do something when the async task was successful
                }
            }
        }.execute();
    }

}
