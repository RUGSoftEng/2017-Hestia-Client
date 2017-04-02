package hestia.backend;

import android.app.Application;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * A singleton class which handles interaction between front and back-end. The facade pattern is
 * used to achieve this. During execution, there is a single ClientInteractionController accessible
 * throughout the entire app through the HestiaApplication class.
 * @see hestia.UI.HestiaApplication
 */
public class ClientInteractionController extends Application{

    // Use CopyOnWriteArrayList to avoid ConcurrentModificationExceptions if a
    // listener attempts to remove itself during event notification.
    private final CopyOnWriteArrayList<DevicesChangeListener> listeners =
            new CopyOnWriteArrayList<DevicesChangeListener>();

    private static ClientInteractionController instance;
    private ArrayList<Device> devices = new ArrayList<>();
    private final static String TAG = "ClntInterController";
    private String ip = "192.168.178.30";
    private int port = 8000;

    /**
     * The empty constructor, which can not be accessed from the outside, because we want a
     * singleton behavior.
     */
    private ClientInteractionController(){}

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setPort(int port){
        this.port = port;
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return port;
    }

    public String getPath(){
        return "http://" + ip + ":" + port + "/";
    }

    public void updateDevices(){
        String path = "http://" + ip + ":" + port + "/";
        new DeviceListRetrieverTask(path).execute();
    }

    /**
     * This method will return a list of devices, which is possibly empty. If this is the case, then
     * an AsyncTask is started to fill the devices so that the devices will eventually be filled.
     * @return a list of devices known to the server.
     */
    public ArrayList<Device> getDevices(){
        if(devices.isEmpty()){
            updateDevices();
        }
        return devices;
    }

    /**
     * This method implements the HTTP POST method for changing the state of an activator on a
     * device as an AsyncTask. It will continue trying to post until the response is not an error.
     * @param device The target device for the post
     * @param activatorId The target activator for the post
     * @param newState The new state object to be used by the post
     * @see StateModificationTask
     */
    public void setActivatorState(Device device, int activatorId, ActivatorState newState){
        Activator activator = device.getActivators().get(activatorId);
        activator.setState(newState);
        new StateModificationTask(device.getDeviceId(),activatorId,newState,this).execute();
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
        fireChangeEvent();
    }

    public static ClientInteractionController getInstance(){
        if(instance == null){
            instance = new ClientInteractionController();
        }
        return instance;
    }

    public void addDevicesChangeListener(DevicesChangeListener l) {
        this.listeners.add(l);
    }

    public void removeDevicesChangeListener(DevicesChangeListener l) {
        this.listeners.remove(l);
    }

    protected void fireChangeEvent() {
        DevicesEvent evt = new DevicesEvent(this);
        for (DevicesChangeListener l : listeners) {
            l.changeEventReceived(evt);
        }
    }

}