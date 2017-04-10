package hestia.backend;

import java.util.ArrayList;

/**
 * Represents the internal representation of the device class on the client. The device contains an
 * id with which we can reference its remote version on the server. The name string contains the
 * local name of the device, for instance "Front door lock". The type string is used to denote the
 * type of the device so a GUI can be generated with the right icons at the correct location.
 * <p>
 *     Finally, there is a list of activators. These activators represent all the actions which can
 *     be performed remotely on the device. An activator can be for instance an On/Off switch, or
 *     an intensity slider.
 * </p>
 * @see Activator
 */
public class Device {
    private int deviceId;
    private String name;
    private String type;
    private ArrayList<Activator> activators;

    public Device(int deviceId, String name, String type, ArrayList<Activator> activator) {
        this.deviceId = deviceId;
        this.name = name;
        this.type = type;
        this.activators = activator;
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

    public Activator getActivator(int activatorId){
        return activators.get(activatorId);
    }

    public ArrayList<Activator> getActivators() {
        return activators;
    }

    public void setActivators(ArrayList<Activator> activators) {
        this.activators = activators;
    }
    public String toString(){
        return name +" "+ deviceId + " " + activators + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (deviceId != device.deviceId) return false;
        if (!name.equals(device.name)) return false;
        if (!type.equals(device.type)) return false;
        return activators.equals(device.activators);

    }

    @Override
    public int hashCode() {
        int result = deviceId;
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + activators.hashCode();
        return result;
    }
}
