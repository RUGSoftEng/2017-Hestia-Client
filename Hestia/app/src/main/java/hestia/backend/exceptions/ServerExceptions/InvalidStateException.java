package hestia.backend.exceptions.ServerExceptions;

import com.google.gson.JsonObject;

public class InvalidStateException extends DefinedServerException {

    String expectedType;
    String valueType;

    public InvalidStateException(String error, JsonObject details){
        super(error);
        expectedType= details.get("expected_type").getAsString();
        valueType= details.get("value_type").getAsString();
    }
}
