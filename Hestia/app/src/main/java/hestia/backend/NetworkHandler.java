package hestia.backend;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import hestia.backend.requests.DeleteRequest;
import hestia.backend.requests.GetDevicesRequest;
import hestia.backend.requests.GetPluginInformationRequest;
import hestia.backend.requests.PostRequest;

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
