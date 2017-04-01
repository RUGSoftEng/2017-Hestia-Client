package hestia.backend;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A singleton class which handles interaction between front and back-end. The facade pattern is
 * used to achieve this. During execution, there is a single ClientInteractionController accessible
 * throughout the entire app through the HestiaApplication class.
 * @see hestia.UI.HestiaApplication
 */
public class ClientInteractionController extends Application{
    private static final ClientInteractionController instance = new ClientInteractionController();
    private ArrayList<Device> devices;
    private final static String TAG = "ClntInterController";
    private String path;
    private String ip = null;
    private int port = 7644;

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

    public ClientInteractionController(){
        try {
        	path = "http://" + ip + ":" + port + "/";
            devices = new DeviceListRetrieverTask(this.path, instance).execute().get();
        } catch (InterruptedException e) {
            Log.e(TAG,e.toString());
        } catch (ExecutionException e) {
            Log.e(TAG,e.toString());
        }
    }

    public ArrayList<Device> getDevices(){
        assert(devices != null);
        return devices;
    }

    /**
     * TODO Check whether POST was successful
     * @param device
     * @param activatorId
     * @param newState
     * @return
     */
    public int setActivatorState(Device device, int activatorId, ActivatorState newState){
        int response = 0;
        Activator activator = device.getActivators().get(activatorId);
        activator.setState(newState);
        try {
            new StateModificationTask(device.getDeviceId(),activatorId,newState,this.path).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public static ClientInteractionController getInstance(){
        return instance;
    }



}
