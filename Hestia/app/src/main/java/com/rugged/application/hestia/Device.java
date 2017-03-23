package com.rugged.application.hestia;

import java.util.ArrayList;

/**
 * This class contains the clients internal representation of the peripheral connected to the
 * server.
 */
public class Device {
    int deviceId;
    String name;
    ArrayList<Activator> activators;

    public Device(int id, String name, String type, ArrayList<Activator> a) {
        this.deviceId = id;
        this.name = name;
        this.activators = a;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public ArrayList<Activator> getActivators() {
        return activators;
    }

    public void setActivators(ArrayList<Activator> activators) {
        this.activators = activators;
    }
    public String toString(){
        return name +" "+ deviceId + " "+activators;
    }
}