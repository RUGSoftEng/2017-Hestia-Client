package hestia.backend;

import android.app.Application;
import com.google.gson.JsonObject;

/**
 * A singleton class which handles interaction between front and back-end. The facade pattern is
 * used to achieve this. During execution, there is a single NetworkHandler accessible
 * throughout the entire app through the HestiaApplication class.
 */

public class NetworkHandler extends Application{
    public String ip;
    public int port;

    public NetworkHandler(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public JsonObject DELETE(JsonObject object, String path){
        return new JsonObject();
    }

    public JsonObject PUT(JsonObject object, String path){
        return new JsonObject();
    }

    public JsonObject POST(JsonObject object, String path){
        return new JsonObject();
    }

    public JsonObject GET(JsonObject object, String path){
        return new JsonObject();
    }
}
