package hestia.backend;

/**
 * Interface used to implement a Listener. It contains only one method,
 * changeEventReceived, which is called once a DevicesEvent was triggered.
 * @see DevicesEvent
 */

public interface DevicesChangeListener {
    void changeEventReceived(DevicesEvent evt);
}
