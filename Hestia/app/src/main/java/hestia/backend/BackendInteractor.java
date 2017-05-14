package hestia.backend;

import android.app.Activity;
import android.app.Application;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import hestia.backend.requests.DeleteRequest;
import hestia.backend.requests.GetDevicesRequest;
import hestia.backend.requests.GetPluginInformationRequest;
import hestia.backend.requests.PostRequest;

/**
 * A singleton class which handles interaction between front and back-end. The facade pattern is
 * used to achieve this. During execution, there is a single BackendInteractor accessible
 * throughout the entire app through the HestiaApplication class.
 * @see hestia.UI.HestiaApplication
 */

public class BackendInteractor extends Application{

    /**
     * We use a CopyOnWriteArrayList to avoid ConcurrentModificationExceptions if
     * a listener attempts to remove itself during event notification.
     */
    private final CopyOnWriteArrayList<DevicesChangeListener> listeners =
            new CopyOnWriteArrayList<>();
    private static BackendInteractor instance;
    private ArrayList<Device> devices = new ArrayList<>();
    private final static String TAG = "BackendInteractor";

    private String ip = "82.73.173.179";


    //PI:
//    private String ip = "82.73.173.179";
//    private int port = 8022;

    private int port = 8000;


    /**
     * The empty constructor, which can not be accessed from the outside,
     * because we want a singleton behavior.
     */
    private BackendInteractor(){}

    /**
     * Returns the single instance of BackendInteractor.
     * If there was no instance of this class created previously,
     * then it will create one and return it
     * @return the single instance of BackendInteractor
     */
    public static BackendInteractor getInstance(){
        if(instance == null){
            instance = new BackendInteractor();
        }
        return instance;
    }

    /**
     * Deletes a device from the server by starting a DeleteRequest,
     * which will send a DELETE request to the server.
     * @param device the device to be deleted.
     * @see DeleteRequest
     */
    public void deleteDevice(Device device) {
        String id = device.getId();
        String path = this.getPath() + "devices/" + id;
        new DeleteRequest(path).execute();
        this.updateDevices();
    }

    /**
     * Adds a device to the server, by starting a GetPluginInformationRequest,
     * which will send a GET request to the server and, based on the data returned from
     * the GET request, will do a POST request with additional information.
     * @param organisation the organization that has/manufactured the device. (e.g. Philips)
     * @param pluginName the name of the plugin the contains data of the device to be added
     * @param activity the current activity
     * @see GetPluginInformationRequest
     */
    public void addDevice(String organisation, String pluginName, Activity activity) {
        String path = this.getPath() + "plugins/" + organisation + "/plugins/" + pluginName;
        new GetPluginInformationRequest(path, activity).execute();
    }

    /**
     * Executes a POST request, sending a jsonObject containing all information
     * needed to add a device to the server.
     * @param requiredInfo the jsonObject containing the information relevant to adding a new device.
     */
    public void postDevice(JsonObject requiredInfo) {
        String path = BackendInteractor.getInstance().getPath() + "devices/";
        new PostRequest(path, requiredInfo).execute();
        BackendInteractor.getInstance().updateDevices();
    }

    /**
     * Updates the current list of devices by running the GetDevicesRequest, which
     * will execute a GET request for the list of devices from the server.
     */
    public void updateDevices(){
        String devicesPath = this.getPath() + "devices/";
        new GetDevicesRequest(devicesPath).execute();
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

    /**
     * This method implements the HTTP POST method for changing the state of an activator on a
     * device as an AsyncTask.
     * @param device The target device for the post
     * @param activator The target activator for the post
     * @param newActivatorState The new state object to be used by the post
     * @see PostRequest
     */
    public void setActivatorState(Device device, Activator activator, ActivatorState newActivatorState){
        activator.setState(newActivatorState);
        String deviceId = device.getId();
        String activatorId = activator.getId();
        String path = this.getPath() + "devices/" + deviceId + "/activators/" + activatorId;
        JsonObject newState = new JsonObject();
        JsonPrimitive newStateValue = this.getNewStateValue(newActivatorState);
        newState.add("state", newStateValue);
        new PostRequest(path, newState).execute();
    }

    /**
     * Creates a JsonPrimitive containing the raw value of the new state.
     * The raw state can be either a Boolean or a Number. In case the state is something else,
     * it will return a String.
     * @param newActivatorState the new state of the device
     * @return JsonPrimitive containing the rawValue
     */
    private JsonPrimitive getNewStateValue(ActivatorState newActivatorState) {
        switch (newActivatorState.getType().toLowerCase()) {
            case "bool":
                return new JsonPrimitive(Boolean.valueOf(String.valueOf(newActivatorState.getRawState())));
            case "float":
                return new JsonPrimitive(Float.valueOf(String.valueOf(newActivatorState.getRawState())));
            default:
                return new JsonPrimitive(String.valueOf(newActivatorState.getRawState()));
        }
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
     * Returns the path to the main page of the server.
     * This consists of the server's IP address and port number.
     * @return the path to the main page of the server
     */
    public String getPath(){
        return "http://" + ip + ":" + port + "/";
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

    /**
     * This overloaded version of addDevice is used exclusively for testing purposes.
     */
    public void addDevice(Device device){
        devices.add(device);
    }

    public void deleteTestDevice(int deviceId) {
        devices.remove(deviceId);
    }

    public void clearDevices(){
        devices = new ArrayList<>();
    }
}
