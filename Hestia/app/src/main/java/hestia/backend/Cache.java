package hestia.backend;


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cache {
    /**
     * We use a CopyOnWriteArrayList to avoid ConcurrentModificationExceptions if
     * a listener attempts to remove itself during event notification.
     */
    private final CopyOnWriteArrayList<DevicesChangeListener> listeners =
            new CopyOnWriteArrayList<>();
    private static Cache instance;
    private ArrayList<Device> devices = new ArrayList<>();
    private final static String TAG = "Cache";

    private String ip = "82.73.173.179";
    private int port = 8000;

    private Cache() {}

    /**
     * Returns the single instance of Cache.
     * If there was no instance of this class created previously,
     * then it will create one and return it
     * @return the single instance of Cache
     */
    public static Cache getInstance(){
        if(instance == null){
            instance = new Cache();
        }
        return instance;
    }

    public ArrayList<Device> getDevices(){
        return devices;
    }

    /**
     * Replaces the current list of devices with the specified one and will fire a change event.
     * @param devices the new list of devices
     */
    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
        fireChangeEvent();
    }

    public String getIp(){
        return this.ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }

    /**
     * Triggers a change event. The change is propagated to all listeners.
     * @see hestia.UI.DeviceListFragment
     */
    protected void fireChangeEvent() {
        DevicesEvent evt = new DevicesEvent(this);
        for (DevicesChangeListener l : listeners) {
            l.changeEventReceived(evt);
        }
    }

    /**
     * Adds a DeviceChangeListener to the list of listeners.
     * @param l the listener to be added to the list of listeners.
     */
    public void addDevicesChangeListener(DevicesChangeListener l) {
        this.listeners.add(l);
    }

    /**
     * Removes a DeviceChangeListener from the the list of listeners.
     * @param l the listener to be removed from the list of listeners.
     */
    public void removeDevicesChangeListener(DevicesChangeListener l) {
        this.listeners.remove(l);
    }

    public CopyOnWriteArrayList<DevicesChangeListener> getListeners(){
        return this.listeners;
    }

}
