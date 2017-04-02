package hestia.UIWidgets;

import hestia.backend.Activator;
import hestia.backend.Device;

public interface UIWidget {
    Device getDevice();
    void setActivatorId(Activator id);

    Activator getActivatorId();

}
