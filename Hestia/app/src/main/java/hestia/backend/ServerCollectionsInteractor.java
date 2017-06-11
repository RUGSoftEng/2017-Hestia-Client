package hestia.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.exceptions.ExceptionFactory;
import hestia.backend.exceptions.ServerExceptions.ServerException;
import hestia.backend.models.Device;
import hestia.backend.models.deserializers.DeviceDeserializer;
import hestia.backend.models.RequiredInfo;
import hestia.backend.models.deserializers.RequiredInfoDeserializer;

/**
 * This class is a facade, performing the basic operations between the User and the Server.
 * It does so using the NetworkHandler, which implements 4 requests methods:
 * GET, POST, PUT and DELETE.
 * @see NetworkHandler
 */
public class ServerCollectionsInteractor implements Serializable{
    private NetworkHandler handler;
    ExceptionFactory factory;

    public ServerCollectionsInteractor(NetworkHandler handler){
        this.handler = handler;
    }

    public ArrayList<Device> getDevices() throws IOException, ServerException {
        String endpoint = "devices/";
        JsonElement payload = handler.GET(endpoint);
        if(payload.isJsonArray()) {
            JsonArray jsonArray = payload.getAsJsonArray();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Device.class, new DeviceDeserializer(handler));
            Gson gson = gsonBuilder.create();

            Type type = new TypeToken<ArrayList<Device>>(){}.getType();
            ArrayList<Device> devices = gson.fromJson(jsonArray, type);
            this.connectDevicesToHandler(devices);
            return devices;
        } else {
            JsonObject jsonObject = payload.getAsJsonObject();
            factory=new ExceptionFactory(jsonObject.get("error").getAsJsonObject());
            throw factory.getException();
        }
    }

    public void sendLoginData(String username, String password) throws IOException, ServerException {
        JsonObject loginData = new JsonObject();
        loginData.addProperty("username",username);
        loginData.addProperty("password",password);
        String endpoint = "login/";
        JsonElement result = handler.PUT(loginData,endpoint);
        if(result.isJsonObject()){
            JsonObject object = result.getAsJsonObject();
            if(object.has("error")){
                factory=new ExceptionFactory(object.get("error").getAsJsonObject());
                throw factory.getException() ;
            }
        }
    }

    public void addDevice(RequiredInfo info) throws IOException,ServerException {
        JsonObject send = new JsonObject();
        send.addProperty("collection", info.getCollection());
        send.addProperty("plugin_name", info.getPlugin());
        JsonObject required = new JsonObject();
        for(String key : info.getInfo().keySet()){
            required.addProperty(key, info.getInfo().get(key));
        }
        send.add("required_info", required);
        String endpoint = "devices/";
        JsonElement payload = handler.POST(send, endpoint);
        if(payload != null && payload.isJsonObject()) {
            JsonObject object = payload.getAsJsonObject();
            if(object.has("error")) {
              factory=new ExceptionFactory(object.get("error").getAsJsonObject());
                throw factory.getException();
            }
        }
    }

    public void removeDevice(Device device) throws IOException,ServerException {
        String endpoint = "devices/" + device.getId();
        JsonElement payload = handler.DELETE(endpoint);
        if(payload != null && payload.isJsonObject()) {
            JsonObject jsonObject = payload.getAsJsonObject();
            if(jsonObject.has("error")) {
                factory=new ExceptionFactory(jsonObject.get("error").getAsJsonObject());
                throw factory.getException();

            }
        }
    }

    public ArrayList<String> getCollections() throws IOException, ServerException {
        String endpoint = "plugins";
        JsonElement payload = handler.GET(endpoint);
        if(payload.isJsonObject()){
            JsonObject object = payload.getAsJsonObject();
            if(object.has("error")){
                factory=new ExceptionFactory(object.get("error").getAsJsonObject());
                throw factory.getException();
            }
        }
        ArrayList<String> collections = this.parseInfo(payload);
        return collections;
    }

    public ArrayList<String> getPlugins(String collection) throws IOException, ServerException {
        String endpoint = "plugins/" + collection;
        JsonElement payload = handler.GET(endpoint);
        if(payload.isJsonObject()){
            JsonObject object = payload.getAsJsonObject();
            if(object.has("error")){
                factory=new ExceptionFactory(object.get("error").getAsJsonObject());
                throw factory.getException();
            }
        }
        ArrayList<String> plugins = this.parseInfo(payload);
        return plugins;
    }

    public RequiredInfo getRequiredInfo(String collection, String plugin) throws IOException, ServerException {
        String endpoint = "plugins/" + collection + "/plugins/" + plugin;
        JsonElement payload = handler.GET(endpoint);
        RequiredInfo requiredInfo = null;
        if (payload != null && payload.isJsonObject()) {
            JsonObject object = payload.getAsJsonObject();
            if(object.has("error")) {
               factory = new ExceptionFactory(object.get("error").getAsJsonObject());
                throw factory.getException();
            } else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(RequiredInfo.class, new RequiredInfoDeserializer());
                Gson gson = gsonBuilder.create();
                requiredInfo = gson.fromJson(object, RequiredInfo.class);
            }
        }
        return requiredInfo;
    }

    private ArrayList<String> parseInfo(JsonElement element) throws ServerException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ArrayList<String> list = new ArrayList<>();
        if(element != null && element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            list = gson.fromJson(array, new TypeToken<ArrayList<String>>() {}.getType());
        } else if (element != null && element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if(object.has("error")) {
              factory= new ExceptionFactory(object.get("error").getAsJsonObject());
                throw factory.getException();
            }
        }
        return list;
    }

    public NetworkHandler getHandler() {
        return handler;
    }

    public void setHandler(NetworkHandler handler) {
        this.handler = handler;
    }

    private void connectDevicesToHandler(ArrayList<Device> devices) {
        for(Device device : devices) {
            device.setHandler(this.handler);
        }
    }
}
