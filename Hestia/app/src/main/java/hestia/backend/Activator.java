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
    private int rank;
    private ActivatorState state;
    private String name;

    /**
     * Creates an Activator with the specified id, state and name.
     * @param activatorId the id of the activator
     * @param state the current state of the activator, this is a wrapper around a generic type
     * @param name the name of the activator
     */
    public Activator(String activatorId, Integer rank, ActivatorState state, String name) {
        this.activatorId = activatorId;
        this.rank = rank;
        this.state = state;
        this.name = name;
    }

    public String getId() {
        return activatorId;
    }


    public void setId(String activatorId) {
        this.activatorId = activatorId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public ActivatorState getState() {
        return state;
    }

    public void setState(ActivatorState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

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
