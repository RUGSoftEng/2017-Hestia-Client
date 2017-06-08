package hestia.UI;

import android.app.Application;
import android.content.Context;

/**
 * An extension of the Application class provided by Android, which additionally contains the
 * NetworkHandler singleton.
 */

public class HestiaApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }


}
