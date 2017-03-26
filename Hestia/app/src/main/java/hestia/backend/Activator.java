package hestia.backend;


/**
 * This class represents a single activator on a device. A single device can have multiple
 * activators. The activator has an id so we can reference it on the server. Furthermore there is a
 * string name and a string state type, which is currently used for casting the generic state
 * variable to an actual type.
 * @see Device
 * @param <T> Type of the state of the activator. This can be a boolean (for a switch) or a float
 *           (for a slider)
 */
public class Activator {
    private int activatorId;
    private ActivatorState state;
    private String name;

    private String requiredInfo;

    public Activator(int id, ActivatorState state, String name) {
        this.activatorId = id;
        this.state = state;
        this.name = name;
    }

    public int getId() {
        return activatorId;
    }

    public void setId(int id) {
        this.activatorId = id;
    }

    public ActivatorState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return state.getType();
    }

    public void setType(String type) {
        state.setType(type);
    }

    public String toString() {
        return name + " " + state;
    }
}
