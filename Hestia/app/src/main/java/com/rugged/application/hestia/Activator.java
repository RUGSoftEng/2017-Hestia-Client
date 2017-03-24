package com.rugged.application.hestia;


/**
 * This class represents a single activator on a device. A single device can have multiple
 * activators. The activator has an id so we can reference it on the server. Furthermore there is a
 * string name and a string state type, which is currently used for casting the generic state
 * variable to an actual type.
 * @see Device
 * @param <T> Type of the state of the activator. This can be a boolean (for a switch) or a float
 *           (for a slider)
 */
public class Activator<T> {
    private int activatorId;
    private T state;
    private String name;
    private String stateType;

    private String requiredInfo;

    public Activator(int id, T state, String name, String type) {
        this.activatorId = id;
        this.state = state;
        this.name = name;
        this.stateType = type;
    }

    public int getId() {
        return activatorId;
    }

    public void setId(int id) {
        this.activatorId = id;
    }

    public T getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return stateType;
    }

    public void setType(String type) {
        this.stateType = type;
    }

    public String toString() {
        return name + " " + state;
    }
}
