package hestia.UI;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.rugged.application.hestia.R;

import hestia.backend.NetworkHandler;

/**
 * An extension of the Application class provided by Android, which additionally creates
 * the network handler
 */


public class HestiaApplication extends Application {
    private static Context mContext;
    private NetworkHandler networkHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        setupNetworkHandler();
        mContext = this;
    }

    private void setupNetworkHandler() {
        SharedPreferences prefs = getSharedPreferences(
                      getApplicationContext().getString(R.string.hestiaIp), Context.MODE_PRIVATE);
        String ip = prefs.getString( getApplicationContext().getString(R.string.ipOfServer), null);
        networkHandler = new NetworkHandler(ip, Integer.valueOf(getApplicationContext().
                                                                getString(R.string.default_port)));
    }

    public NetworkHandler getNetworkHandler(){
        return networkHandler;
    }

    public void setNetworkHandler(NetworkHandler handler){
        this.networkHandler = handler;
    }

    public static Context getContext(){
        return mContext;
    }
}
