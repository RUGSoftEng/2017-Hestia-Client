package hestia.backend.exceptions.ServerExceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class DatabaseException extends DefinedServerException {

    String type;
    String message;

    public DatabaseException(String error, JsonObject details){
        super(error);
        type= details.get("type").getAsString();
        message=details.get("message").getAsString();
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
