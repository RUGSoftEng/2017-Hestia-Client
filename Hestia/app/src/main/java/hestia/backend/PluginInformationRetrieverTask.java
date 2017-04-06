package hestia.backend;

import java.util.HashMap;
import android.os.AsyncTask;
import android.util.Log;

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

public class PluginInformationRetrieverTask extends AsyncTask<Void,Void,HashMap<String,String>> {
    private static final String TAG = "PluginRetrieverTask";
    private String path;
    private ClientInteractionController cic;

    public PluginInformationRetrieverTask(String path) {
        this.path = path;
        this.cic = ClientInteractionController.getInstance();
    }

    @Override
    protected HashMap<String, String> doInBackground(Void... params) {
        String pluginsPath = path;
        URL url = null;
        HttpURLConnection urlConnection = null;
        HashMap<String,String> plugins = null;
        try {
            url = new URL(pluginsPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            Log.i(TAG, input.toString());
            plugins = readStream(input);
            urlConnection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        Log.i(TAG, "PLUGINS: " + plugins.toString());
        return plugins;
    }
    @Override
    protected void onPostExecute(HashMap<String,String> plugins) {
        cic.setPlugins(plugins);
    }

    private HashMap<String,String> readStream(InputStream is) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type pluginType= new TypeToken<HashMap<String,String>>() {}.getType();
        HashMap<String,String> responseDev = gson.fromJson(gson.newJsonReader(reader), pluginType);
        System.out.println(responseDev);
        reader.close();
        return responseDev;
    }


}
