package hestia.backend;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
import hestia.UI.AddDeviceInfo;


/**
 * Subclass of the AsyncTask, it is used to start a task that will send a GET request
 * for the specified plugin to the server.
 */

public class PluginInformationRetrieverTask extends AsyncTask<Void,Void,HashMap<String,String>> {
    private static final String TAG = "PluginRetrieverTask";
    private String pluginPath;
    private Activity a;

    /**
     * Creates an instance of PluginInformationRetrieverTask, storing the pluginPath and
     * the current activity.
     * @param pluginPath the pluginPath to the required plugin
     * @param a the current activity
     */
    public PluginInformationRetrieverTask(String pluginPath, Activity a) {
        this.pluginPath = pluginPath;
        this.a = a;
    }

    /**
     * This method runs in a separate background thread. It establishes a connection to the server
     * and then calls the readStream method to obtain the actual plugin map
     * @return the hashmap containing the fields which are required for the plugin.
     */
    @Override
    protected HashMap<String, String> doInBackground(Void... params) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        HashMap<String,String> plugin = null;
        try {
            url = new URL(this.pluginPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);
            urlConnection.connect();
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            plugin = readStream(input);
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "SocketTimeoutException");
            Log.e(TAG, e.toString());
        } catch (ConnectException e) {
            Log.e(TAG, "ConnectException");
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return plugin;
    }

    /**
     * At the end of the task, open a window that process the plugin's data.
     * If the plugin is NULL, show a Toast to the user.
     * @see AddDeviceInfo
     */
    @Override
    protected void onPostExecute(HashMap<String,String> plugin) {
        if(plugin != null) {
            new AddDeviceInfo(a, plugin).show();
        } else {
            Toast.makeText(a, "Wrong Input", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Reads the data of the plugin sent by the Server.
     * @param is the input stream which accepts data from the Server
     * @return the plugin from the server
     * @throws IOException if the data could not be received
     */
    private HashMap<String,String> readStream(InputStream is) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type pluginType= new TypeToken<HashMap<String,String>>() {}.getType();
        HashMap<String,String> plugin = gson.fromJson(gson.newJsonReader(reader), pluginType);
        Log.i(TAG, plugin.toString());
        reader.close();
        return plugin;
    }
}
