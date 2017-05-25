package hestia.backend;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import hestia.backend.models.ActivatorState;
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

    public ArrayList<Device> getDevices(){
        JsonObject list = handler.GET(null, "devices");
        // TODO Parse json object into Device list
        // TODO Make sure the activator is given its device id and handler

        ArrayList<Device> devices = new ArrayList<>();

        ArrayList<Activator> activators = new ArrayList<>();
        ActivatorState<Boolean>  state =  new ActivatorState<Boolean>(Boolean.parseBoolean("true"),"bool");
        activators.add(new Activator("Act_1_Bool", "device_id", 0, state, "name", handler));
        devices.add(new Device("device_id", "one", "Light", activators, handler));

        ArrayList<Activator> activatorList = new ArrayList<>();
        ActivatorState<Float> state2 =  new ActivatorState<Float>(Float.parseFloat("0.0"),"float");
        ActivatorState<Boolean>  state3 =  new ActivatorState<Boolean>(Boolean.parseBoolean("true"),"bool");
        activatorList.add(new Activator("Act_2_Float", "ffff", 1, state2, "Naam", handler));
        activatorList.add(new Activator("Act_1_Bool", "ffff", 0, state3, "name", handler));
        devices.add(new Device("device_id", "one", "Lock", activatorList, handler));

        return devices;
    }

    public void addDevice(RequiredInfo info){
        JsonObject send = new JsonObject();
        send.addProperty("collection", info.getCollection());
        send.addProperty("plugin_name", info.getPlugin());
        // TODO add info to json object
        handler.POST(send, "devices");
    }

    public void removeDevice(Device device){
        JsonObject result = handler.DELETE(null, "devices/" + device.getId());
    }

    public ArrayList<String> getCollections(){
        JsonObject object = handler.GET(null, "plugins");
        // TODO Parse json into collections list.
        return new ArrayList<>();
    }

    public ArrayList<String> getPlugins(String collection){
        JsonObject object = handler.GET(null, "plugins/" + collection);
        // TODO Parse json into collections list.
        return new ArrayList<>();
    }

    public RequiredInfo getRequiredInfo(String collection, String plugin){
        JsonObject object = handler.GET(null, "plugins/" + collection + "/plugins/" + plugin);
        // TODO Parse json into required info
        return new RequiredInfo("Collection", "Plugin", new HashMap<String, String>());
    }

    public String getIp(){
        return handler.ip;
    }

    public void setIp(String ip){
        handler.ip = ip;
    }

    public int getPort(){
        return handler.port;
    }

    public void setPort(int port){
        handler.port = port;
    }

}
