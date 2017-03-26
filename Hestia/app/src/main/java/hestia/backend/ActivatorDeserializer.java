package hestia.backend;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import hestia.backend.Activator;

class ActivatorDeserializer implements JsonDeserializer<Activator> {

    @Override
    public Activator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jobject = (JsonObject) json;

        String stateType = jobject.get("stateType").getAsString();
        JsonObject rawState = jobject.get("state").getAsJsonObject();
        ActivatorState state = null;
        switch (stateType){
            case "bool"         : state = new ActivatorState<Boolean>(rawState.getAsBoolean(),"boolean");
                break;
            case "int"          : state = new ActivatorState<Integer>(rawState.getAsInt(),"int");
                break;
        }

        return new Activator(
                jobject.get("id").getAsInt(),
                state,
                jobject.get("pluginType").getAsString());

    }
}
