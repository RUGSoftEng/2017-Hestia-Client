package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class will send a DELETE request to the Server in order to delete
 * the Device "device".
 */

public class RemoveDeviceTask extends AsyncTask<Void, Void, Integer> {
    final String TAG = "RemoveDeviceTask";
    private String path;
    private Device device;

    /**
     * Creates an instance of the RemoveDeviceTask class storing the path and the device passed as arguments.
     * @param path the path to the server. It contains the IP address and Port number.
     * @param device the device to be removed.
     */
    public RemoveDeviceTask(String path, Device device) {
        this.path = path;
        this.device = device;
    }

    /**
     * Send the DELETE request to the server
     */
    @Override
    protected Integer doInBackground(Void... params) {
        // Update the general path to match the one for the device to be deleted.
        int id = this.device.getDeviceId();
        this.path = this.path + "devices/" + id;

        Integer responseCode = null;
        HttpURLConnection httpCon = null;
        try {
            URL url = new URL(this.path);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("DELETE");
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("charset", "utf-8");
            httpCon.connect();
            responseCode = httpCon.getResponseCode();
        } catch (IOException e) {
            Log.e(TAG, "Connection failed: could not realize the DELETE request");
            Log.e(TAG, e.toString());
        } finally {
            if (httpCon != null) {
                httpCon.disconnect();
            }
        }
        return responseCode;
    }
}
