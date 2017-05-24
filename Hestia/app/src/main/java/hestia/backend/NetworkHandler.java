package hestia.backend;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.content.ContentValues.TAG;


/**
 * A singleton class which handles interaction between front and back-end. The facade pattern is
 * used to achieve this. During execution, there is a single NetworkHandler accessible
 * throughout the entire app through the HestiaApplication class.
 */

public class NetworkHandler extends Application{
    private String ip;
    private int port;

    public NetworkHandler(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public JsonElement DELETE(String path) throws IOException {
        HttpURLConnection connector = this.connectToServer("DELETE", path);
        connector.connect();
        JsonElement payload = this.getPayloadFromServer(connector);
        return payload;
    }

    public JsonElement PUT(JsonObject object, String path){
        return new JsonObject();
    }

    public JsonElement POST(JsonObject object, String path){
        return new JsonObject();
    }

    public JsonElement GET(String path){
        return new JsonObject();
    }

    private HttpURLConnection connectToServer(String requestMethod, String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connector = (HttpURLConnection) url.openConnection();
        connector.setReadTimeout(2000);
        connector.setConnectTimeout(2000);
        connector.setRequestMethod(requestMethod);
        connector.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        return connector;
    }

    private JsonElement getPayloadFromServer(HttpURLConnection connector) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Integer responseCode = connector.getResponseCode();
        Log.d(TAG, "Response code: " + responseCode);
        BufferedReader reader;
        if (this.isSuccessfulRequest(responseCode)) {
            reader = new BufferedReader(new InputStreamReader(connector.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connector.getErrorStream()));
        }

        Type returnType = new TypeToken<JsonElement>(){}.getType();
        JsonElement payload = gson.fromJson(gson.newJsonReader(reader), returnType);
        Log.d("GetRequest", "RETURN VALUE IS: \n" + payload.toString());
        reader.close();

        return payload;
    }

    private boolean isSuccessfulRequest(Integer responseCode) {
        if(responseCode == null) return false;
        return (200 <= responseCode && responseCode <= 299);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
