package hestia.backend.NetworkDiscovery;

import android.content.res.Resources;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.rugged.application.hestia.R;

import hestia.backend.NetworkHandler;
import hestia.backend.ServerCollectionsInteractor;

public class HestiaResolveListener implements NsdManager.ResolveListener{
    private static final String SERVICE_NAME = Resources.getSystem().getString(R.string.ServiceName);
    private String TAG = "HestiaRListener";
    private ServerCollectionsInteractor interactor;

    public HestiaResolveListener(ServerCollectionsInteractor interactor){
        this.interactor = interactor;
    }

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
        interactor.getHandler().setIp(serviceInfo.getHost().getHostAddress());
        interactor.getHandler().setPort(serviceInfo.getPort());
    }

    public ServerCollectionsInteractor getUpdatedInteractor(){
        return interactor;
    }
}
