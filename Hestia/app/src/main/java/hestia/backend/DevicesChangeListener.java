package hestia.backend;

/**
 * Interface used to implement a Listener. It contains only one method,
 * changeEventReceived, which is called once a DeviceEvent was triggered.
 */

public interface DevicesChangeListener {
    void changeEventReceived(DevicesEvent evt);
}
