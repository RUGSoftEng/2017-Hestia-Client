package com.rugged.application.hestia;

import android.util.Log;

import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ClientInteractionController {
    ArrayList<Device> devices;
    private final static String TAG = "ClntInterController";
    private String path;

    public ClientInteractionController(String path){
        this.path = path;

        try {
            devices = new DeviceListRetrieverTask(path).execute().get();
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

    public int setActivatorState(Device d, int actId, activatorState newState){
        int response;
        Activator a = d.getActivators().get(actId);
        new StateModificationTask(a, newState).execute().get(response);
        return response;
    }
}
