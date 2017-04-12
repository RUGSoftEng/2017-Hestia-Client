package hestia.backend;

/**
 * Wrapper class for the different state fields. The activator state has a type T, which is
 * inferred using a custom JSON deserializer.
 * @see ActivatorDeserializer
 * @param <T> Type of the state of the activator. This can be a boolean (for a switch) or a float
 *           (for a slider)
 */

public class ActivatorState<T> {
    T state;
    String type;

    /**
     * Creates an ActivatorState with the specified rawState and type.
     * @param state the value of the ActivatorState, based on the type
     * @param type the type of the activator.
     */
    public ActivatorState(T state, String type) {
        state = state;
        this.type = type;
    }

    /**
     * Returns the state of the activator.
     * @return the state of the activator
     */
    public T getState() {
        return this.state;
    }

    /**
     * Replaces the current state of the ActivatorState with the specified one.
     * @param state the new state of the ActivatorState
     */
    public void setState(T state){
        this.state = state;
    }

    /**
     * Returns the type of the activator.
     * @return the type of the activator
     */
    public String getType(){
        return this.type;
    }

    /**
     * Replaces the current type of the ActivatorState with the specified one.
     * @param type the new type of the activator
     */
    public void setType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type + " - " + this.state.toString();
    }
}
