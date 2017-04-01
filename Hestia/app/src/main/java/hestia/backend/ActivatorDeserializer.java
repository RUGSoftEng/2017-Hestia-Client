package hestia.backend;

import android.util.Log;

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
        Log.i("JSOBONJECT",jobject.toString());
        String stateType = jobject.get("stateType").getAsString();
        String rawState = jobject.get("state").getAsString();
        ActivatorState state = null;
        switch (stateType) {
            case "bool":
                state = new ActivatorState<Boolean>(Boolean.parseBoolean(rawState),"TOGGLE");
                break;
            case "int":
                state = new ActivatorState<Integer>(Integer.parseInt(rawState),"SLIDER");
                break;
        }

        return new Activator(
                jobject.get("activatorId").getAsInt(),
                state,
                jobject.get("name").getAsString());

    }
}
