package hestia.backend.refactoring;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import hestia.backend.Activator;
import hestia.backend.ActivatorDeserializer;
import hestia.backend.Device;

public class GetRequest extends Request {

    public GetRequest(String requestType, String path) {
        super(requestType, path);
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {
        urlConnection.setDoInput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) throws IOException {

        /*GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Activator.class, new ActivatorDeserializer());
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Type deviceListType= new TypeToken<ArrayList<Device>>() {}.getType();
        ArrayList<Device> responseDev = gson.fromJson(gson.newJsonReader(reader), deviceListType);
        Log.i(TAG, "ResponseDev: " + (responseDev != null ? responseDev.toString() : "NULL"));
        reader.close();
        return responseDev;

// -------------------------------------------------
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Type pluginType= new TypeToken<HashMap<String,String>>() {}.getType();
        HashMap<String,String> plugin = gson.fromJson(gson.newJsonReader(reader), pluginType);
        Log.i(TAG, plugin.toString());
        reader.close();
        return plugin;
        */
    }
}
