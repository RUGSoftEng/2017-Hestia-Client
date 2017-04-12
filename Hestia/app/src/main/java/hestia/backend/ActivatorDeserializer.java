package hestia.backend;

/**
 * A JSON deserializer for an Activator.
 * It implements the Google's JsonDeserializer interface.
 * @see Activator
 */

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ActivatorDeserializer implements JsonDeserializer<Activator> {

    /**
     * Deserializes a JSON object, creating an Activator.
     * @param json the JSON object to be deserialized
     * @param typeOfT the type of the Object to deserialize to
     * @param context the current context of application
     * @return a deserialized object of the specified type Activator
     * @throws JsonParseException if json is not in the expected format.
     */
    @Override
    public Activator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = (JsonObject) json;
        Log.i("JSONOBJECT",jsonObject.toString());
        String stateType = jsonObject.get("type").getAsString();
        String rawState = jsonObject.get("state").getAsString();
        ActivatorState state = null;

        switch (stateType.toLowerCase()) {
            case "bool":
                state = new ActivatorState<Boolean>(Boolean.parseBoolean(rawState),"TOGGLE");
                break;
            case "int":
                state = new ActivatorState<Float>(Float.parseFloat(rawState),"SLIDER");
                break;
            case "unsigned_int8" :
                state = new ActivatorState<Float>(Float.parseFloat(rawState),"UNSIGNED_BYTE");
                break;
            case "unsigned_int16" :
                state = new ActivatorState<Float>(Float.parseFloat(rawState),"UNSIGNED_INT16");
                break;
            default : break;
        }

        int activatorId = jsonObject.get("activatorId").getAsInt();
        String name = jsonObject.get("name").getAsString();

        return new Activator(activatorId, state, name);
    }
}
