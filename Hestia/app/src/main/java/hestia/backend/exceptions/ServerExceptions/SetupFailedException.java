package hestia.backend.exceptions.ServerExceptions;

import com.google.gson.JsonObject;

public class SetupFailedException extends DefinedServerException {
    String field;
    String hint;

    public SetupFailedException(String error, JsonObject details){
        super(error);
        field=details.get("field").getAsString();
        hint=details.get("hint").getAsString();
    }
}
