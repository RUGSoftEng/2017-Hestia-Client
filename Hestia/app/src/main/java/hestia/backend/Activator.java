package hestia.backend;

/**
 * This class represents a single activator on a device. A single device can have multiple activators.
 * The activator has an id so we can reference it on the server.
 * Furthermore there is a string name and a string state name,
 * which is currently used for casting the generic state variable to an actual name.
 * @see Device
 * @see ActivatorState
 */
public class Activator {
    private String activatorId;
    private ActivatorState state;
    private String name;

    /**
     * Creates an Activator with the specified id, state and name.
     * @param id the id of the activator
     * @param state the current state of the activator, this is a wrapper around a generic name
     * @param name the name of the activator
     */
    public Activator(String id, ActivatorState state, String name) {
        this.activatorId = id;
        this.state = state;
        this.name = name;
    }

    /**
     * Returns the id of the activator.
     * @return the id of the activator
     */
    public String getId() {
        return activatorId;
    }

    /**
     * Replaces the current id of the activator with the specified one.
     * @param id the new id of the activator
     */
    public void setId(String id) {
        this.activatorId = id;
    }

    /**
     * Returns the current state of the activator.
     * @return the current state of the activator
     */
    public ActivatorState getState() {
        return state;
    }

    /**
     * Replaces the current state of the activator with the specified one.
     * @param state the new state of the activator
     */
    public void setState(ActivatorState state) {
        this.state = state;
    }

    /**
     * Returns the name of the activator.
     * @return the name of the activator
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces the current name of the activator with the specified one.
     * @param name the new name of the activator
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activator activator = (Activator) o;

        if (activatorId != activator.activatorId) return false;
        if (!state.equals(activator.state)) return false;
        return name.equals(activator.name);

    }

    @Override
    public int hashCode() {
        int result = activatorId.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + " " + state;
    }
}
