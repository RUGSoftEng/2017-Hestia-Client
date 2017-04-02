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

    public ActivatorState(T rawState, String type) {
        state = rawState;
        this.type = type;
    }

    public void setState(T rawState){
        state = rawState;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public String toString(){
        return state.toString();
    }
}
