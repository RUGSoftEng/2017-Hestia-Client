package hestia.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import hestia.backend.models.Device;
import hestia.backend.models.RequiredInfo;

/**
 * A singleton class acts as a temporary memory, storing the data regarding the list of devices,
 * IP address, or port number. During execution, there is a single Cache accessible.
 */
public class Cache {
    private NetworkHandler handler;

    public Cache(NetworkHandler handler){
        this.handler = handler;
    }

    public ArrayList<Device> getDevices() throws IOException {
        JsonElement payload = handler.GET("devices");
        // TODO Parse json object into Device list
        // TODO Make sure the activator is given its device id and handler
        return new ArrayList<>();
    }

    public void addDevice(RequiredInfo info) throws IOException {
        JsonObject send = new JsonObject();
        send.addProperty("collection", info.getCollection());
        send.addProperty("plugin_name", info.getPlugin());
        // TODO add info to json object
        handler.POST(send, "devices");
    }

    public void removeDevice(Device device) throws IOException {
        JsonElement result = handler.DELETE("devices/" + device.getId());
    }

    public ArrayList<String> getCollections() throws IOException, ComFaultException {
        JsonElement object = handler.GET("plugins");
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ArrayList<String> list =new ArrayList<String>();
        list=gson.fromJson(object,ArrayList.class);
        if(object.getAsJsonObject().has("error") || list.isEmpty()){
            ComFaultException comFaultException=gson.fromJson(object,ComFaultException.class);
            throw comFaultException;
        }
        return list;
    }

    public ArrayList<String> getPlugins(String collection) throws IOException {
        JsonElement object = handler.GET("plugins/" + collection);
        // TODO Parse json into collections list.
        return new ArrayList<>();
    }

    public RequiredInfo getRequiredInfo(String collection, String plugin) throws IOException {
        JsonElement object = handler.GET("plugins/" + collection + "/plugins/" + plugin);
        // TODO Parse json into required info
        return new RequiredInfo("Collection", "Plugin", new HashMap<String, String>());
    }

    public NetworkHandler getHandler() {
        return handler;
    }

    public void setHandler(NetworkHandler handler) {
        this.handler = handler;
    }
}
