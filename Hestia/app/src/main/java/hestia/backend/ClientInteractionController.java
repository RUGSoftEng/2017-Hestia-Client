package hestia.backend;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ClientInteractionController {
    ArrayList<Device> devices;
    private final static String TAG = "ClntInterController";
    private String path;
    private String ip = "10.0.0.2";
    private int port = 5000;

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
            devices = new DeviceListRetrieverTask(this.path).execute().get();
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
}
