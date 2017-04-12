package hestia.backend;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A singleton class which handles interaction between front and back-end. The facade pattern is
 * used to achieve this. During execution, there is a single ClientInteractionController accessible
 * throughout the entire app through the HestiaApplication class.
 * @see hestia.UI.HestiaApplication
 */
public class ClientInteractionController extends Application{

    /**
     * Use CopyOnWriteArrayList to avoid ConcurrentModificationExceptions if
     * a listener attempts to remove itself during event notification.
     */
    private final CopyOnWriteArrayList<DevicesChangeListener> listeners =  new CopyOnWriteArrayList<>();
    private static ClientInteractionController instance;
    private ArrayList<Device> devices = new ArrayList<>();
    private final static String TAG = "ClntInterController";
    private String ip = "145.97.183.6";
    private int port = 8000;

    /**
     * The empty constructor, which can not be accessed from the outside,
     * because we want a singleton behavior.
     */
    private ClientInteractionController(){}

    /**
     * Returns the single instance of ClientInteractionController.
     * If there was no instance of this class creates previously,
     * then it will create one before returning it.
     * @return the single instance of ClientInteractionController
     */
    public static ClientInteractionController getInstance(){
        if(instance == null){
            instance = new ClientInteractionController();
        }
        return instance;
    }

    /**
     * Deletes a device from the server by starting a RemoveDeviceTask,
     * which will send a DELETE request to the server.
     * @param device the device to be deleted.
     * @see RemoveDeviceTask
     */
    public void deleteDevice(Device device) {
        new RemoveDeviceTask(device).execute();
    }

    /**
     * Adds a device to the server, by starting a PluginInformationRetrieverTask,
     * which will send a GET request to the server and, based on the data returned from
     * the GET request, will create a POST request which will contain additional fields.
     * @param organisation the organization that has/manufactured the device. (e.g. Philips)
     * @param pluginName the name of the plugin the contains data of the device to be added
     * @param activity the current activity
     * @see PluginInformationRetrieverTask
     */
    public void addDevice(String organisation, String pluginName, Activity activity) {
        String path = this.getPath() + "plugins/" + organisation + "/plugins/" + pluginName;
        new PluginInformationRetrieverTask(path, activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Updates the current list of devices by running the DeviceListRetrieverTask, which
     * will execute a GET request for the list of devices from the server.
     */
    public void updateDevices(){
        new DeviceListRetrieverTask().execute();
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
     * Attempts to replace the list of devices with the specified one.
     * If the new list of devices contains different devices, it repalce the old list of devices
     * with the new one and it will fire a change event. Otherwise, it will not do anything.
     * @param devices the new list of devices
     */
    public void setDevices(ArrayList<Device> devices) {
        if(!this.devices.equals(devices)) {
            this.devices = devices;
            fireChangeEvent();
        }
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
        new StateModificationTask(device.getDeviceId(),activatorId,newState).execute();
    }

    /**
     * Returns the IP of the server.
     * @return the IP of the server
     */
    public String getIp(){
        return this.ip;
    }

    /**
     * Replaces the IP of the server with the specified one.
     * This is will also cause the list of devices to be updated.
     * @param ip the IP of the server
     */
    public void setIp(String ip){
        this.ip = ip;
        updateDevices();
    }

    /**
     * Returns the port number of the server.
     * @return the port number of the server
     */
    public int getPort(){
        return port;
    }

    /**
     * Replaces the port number of the server with the specified one.
     * @param port the port number of the server
     */
    public void setPort(int port){
        this.port = port;
    }

    /**
     * Returns the path to the main page of the server.
     * This consists of the server's IP address and port number.
     * @return the path to the main page of the server
     */
    public String getPath(){
        return "http://" + ip + ":" + port + "/";
    }

    /**
     * Triggers a change event.
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
}