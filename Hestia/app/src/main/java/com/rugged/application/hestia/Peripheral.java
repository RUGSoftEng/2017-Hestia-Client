package com.rugged.application.hestia;

import java.util.ArrayList;

/**
 * This class contains the clients internal representation of the peripheral connected to the
 * server.
 */
public class Peripheral {
    private int deviceId;
    private String name;
    private String type;
    ArrayList<Activator> activators;

    public Device(int id, String name, String type, ArrayList<Activator> a) {
        this.deviceId = id;
        this.name = name;
        this.type = type;
        this.activators = a;
    }

    public int getId() {
        return deviceId;
    }

    public void setId(int id) {
        this.deviceId = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}