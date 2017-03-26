package hestia.backend;

import com.google.gson.JsonObject;

/**
 * Wrapper class for the different state fields which are
 */

public class ActivatorState<T> {
    T state;
    String type;

    public ActivatorState(T rawState, String type) {
        state = rawState;
        this.type = type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}