package hestia.backend.models;

import com.google.gson.JsonPrimitive;
import com.rugged.application.hestia.R;
import hestia.UI.HestiaApplication;
import hestia.backend.models.deserializers.ActivatorDeserializer;

/**
 * Wrapper class for the different state fields. The activator state has a type T, which is
 * inferred using a custom JSON deserializer.
 *
 * @param <T> Type of the state of the activator. This can be a boolean (for a switch) or a float
 *            (for a slider).
 * @see ActivatorDeserializer
 */

public class ActivatorState<T> {
    private T rawState;
    private String type;

    /**
     * Creates an ActivatorState with the specified rawState and type.
     *
     * @param rawState the value of the ActivatorState, based on the type
     * @param type     the type of the activator.
     */
    public ActivatorState(T rawState, String type) {
        this.rawState = rawState;
        this.type = type;
    }

    public T getRawState() {
        return this.rawState;
    }

    public void setRawState(T rawState){
        this.rawState = rawState;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    /**
     * Get the JSON representation of the RawS State, which could be a Boolean, a Float or a String.
     * @return the JSON representation of the RawState.
     */
    public JsonPrimitive getRawStateJSON() {
        switch (this.getType().toLowerCase()) {
            case "bool":
                return new JsonPrimitive(Boolean.valueOf(String.valueOf(this.getRawState())));
            case "float":
                return new JsonPrimitive(Float.valueOf(String.valueOf(this.getRawState())));
            default:
                return new JsonPrimitive(String.valueOf(this.getRawState()));
        }
    }


    @Override
    public String toString() {
        return this.type + " - " + this.rawState.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ActivatorState)) return false;
        ActivatorState activatorState = (ActivatorState) object;
        return (this == activatorState || (this.getType().equals(activatorState.getType()) &&
                this.getRawState().equals(activatorState.getRawState())));

    }

    @Override
    public int hashCode() {
        int multiplier = Integer.valueOf(HestiaApplication.getContext().getString(R.string.hashCodeMultiplier));
        int result = getRawState().hashCode() * multiplier + getType().hashCode();
        return result;
    }
}
