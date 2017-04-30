package hestia.UI;

import android.app.Application;

import hestia.backend.ClientInteractionController;

/**
 * An extension of the Application class provided by Android, which additionally contains the
 * ClientInteractionController singleton.
 * @see hestia.backend.ClientInteractionController
 */

public class HestiaApplication extends Application {
    private static final ClientInteractionController cic = ClientInteractionController.getInstance();

    public ClientInteractionController getCic() {
        return cic;
    }

}
