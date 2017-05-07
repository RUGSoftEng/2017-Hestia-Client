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

    /**
     * Gets the deviceId.
     * @return the remote deviceId
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the local deviceId.
     * @param deviceId the Id to be set
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Gets the remote device name.
     * @return the name of the device as stored on the server
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the local name of the device.
     * @param name the local name of the device
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the device.
     * @return a string with the type of the device
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the local type of the device.
     * @param type the new local type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns an activator based on its Id.
     * @param activatorId the Id of the activator
     * @return the activator with the specified Id
     */
    public Activator getActivator(int activatorId){
        return activators.get(activatorId);
    }

    /**
     * Gets the complete list of activators.
     * @return the list of activators
     */
    public ArrayList<Activator> getActivators() {
        return activators;
    }

    /**
     * This method will return all activators which need to be implemented in the UI as sliders.
     * @see hestia.UI.ExpandableListAdapter
     * @return the activators if the array is not empty, null otherwise
     */
    public ArrayList<Activator> getSliders() {
        ArrayList<Activator> sliders = new ArrayList<>();
        for(Activator a : activators){
            String type = a.getState().getType();
            if(type.equals("SLIDER")||type.equals("UNSIGNED_BYTE")||type.equals("UNSIGNED_INT16")){
                sliders.add(a);
            }
        }
        return (sliders.isEmpty() ? null : sliders);
    }

    /**
     * Sets the local activators to a different list.
     * @param activators the list of activators which will be set
     */
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
