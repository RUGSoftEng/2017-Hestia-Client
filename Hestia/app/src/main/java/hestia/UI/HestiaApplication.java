package hestia.UI;

import android.app.Application;

import hestia.backend.BackendInteractor;

/**
 * An extension of the Application class provided by Android, which additionally contains the
 * BackendInteractor singleton.
 * @see BackendInteractor
 */

public class HestiaApplication extends Application {
    private static final BackendInteractor cic = BackendInteractor.getInstance();

    public BackendInteractor getCic() {
        return cic;
    }

}
