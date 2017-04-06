package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostDeviceTask extends AsyncTask<Void, Void, Integer> {

    private final String TAG = "PostDeviceTask";
    private String path;
    private Device device;
    private Activator activator;

    public PostDeviceTask(String path, Device device, Activator activator) {
        this.path = path;
        this.device = device;
        this.activator = activator;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer response = null;
        int deviceId = this.device.getDeviceId();
        int activatorId = this.activator.getId();
        String activatorPath = path + "devices/" + deviceId + "/activators/" + activatorId;
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(activatorPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            OutputStream deviceOutputStream = urlConnection.getOutputStream();
            writeStream(deviceOutputStream);
            response = urlConnection.getResponseCode();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            Log.i(TAG, "Got an exception");
        }
        // Currently, we are not externally handling the HTML response code.
        return response;
    }

    /**
     * Write the new state to the output stream, which is sent over the urlConnection
     */
    private void writeStream(OutputStream os) throws IOException {
        JsonObject json = new JsonObject();
        //JsonPrimitive jPrimitive = new JsonPrimitive(String.valueOf(newState));
        //json.add("state", jPrimitive);
        Log.i(TAG,json.toString());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
        outputStreamWriter.write(json.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
