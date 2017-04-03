package hestia.backend;

import java.util.EventObject;

/**
 * This event is sent as a signal to listeners about a change in the list of devices.
 * @see DevicesChangeListener
 */
public class DevicesEvent extends EventObject {
    public DevicesEvent(Object source) {
        super(source);
    }
}
