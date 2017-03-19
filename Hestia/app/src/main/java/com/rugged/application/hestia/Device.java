package com.rugged.application.hestia;

import java.util.ArrayList;

/**
 * Created by Mark on 19-3-2017.
 */

public class Device {
    int deviceId;
    String name;
    String type;
    ArrayList<Activator> activators;

    public Device(int id, String name, String type, ArrayList<Activator> a) {
        this.deviceId = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Activator> getActivators() {
        return activators;
    }

    public void setActivators(ArrayList<Activator> activators) {
        this.activators = activators;
    }
}
