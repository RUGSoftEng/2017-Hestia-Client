package hestia.backend;

import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import hestia.UI.AddDeviceInfo;

public class PluginInformationRetrieverTask extends AsyncTask<Void,Void,HashMap<String,String>> {
    private static final String TAG = "PluginRetrieverTask";
    private String path;
    private Activity a;

    public PluginInformationRetrieverTask(String path, Activity a) {
        this.path = path;
        this.a = a;
    }

    @Override
    protected HashMap<String, String> doInBackground(Void... params) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        HashMap<String,String> plugins = null;
        try {
            url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            Log.i(TAG, input.toString());
            plugins = readStream(input);

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        Log.i(TAG, "PLUGINS: " + (plugins != null ? plugins.toString() : "NULL"));
        return plugins;
    }

    @Override
    protected void onPostExecute(HashMap<String,String> plugins) {
        if(plugins != null) {
            new AddDeviceInfo(a, plugins).show();
        } else {
            Toast.makeText(a, "Wrong Input", Toast.LENGTH_SHORT).show();
        }
    }

    private HashMap<String,String> readStream(InputStream is) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type pluginType= new TypeToken<HashMap<String,String>>() {}.getType();
        HashMap<String,String> responseDev = gson.fromJson(gson.newJsonReader(reader), pluginType);
        Log.i(TAG, responseDev.toString());
        reader.close();
        return responseDev;
    }
}
