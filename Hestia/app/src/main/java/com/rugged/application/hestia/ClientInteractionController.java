package com.rugged.application.hestia;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ClientInteractionController {
    ArrayList<Device> devices;
    private final static String TAG = "ClntInterController";
    private String path;
    private String ip;
    private int port;

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

    public ClientInteractionController(String path){
        this.path = path;

        try {
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

    public int setActivatorState(Device d, int actId, ActivatorState newState){
        int response=0;
        Activator a = d.getActivators().get(actId);
        try {
            new StateModificationTask(a, newState).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response;
    }
}
