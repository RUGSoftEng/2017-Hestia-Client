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

public class RemoveDeviceTask extends AsyncTask<Void, Void, Void> {
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
    protected Void doInBackground(Void... params) {
        this.updatePath();
        // UPDATE PATH TO MATCH THE CORRECT DELETE ONE
        URL url = null;
        HttpURLConnection httpCon = null;
        try {
            url = new URL(path);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("DELETE");
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("charset", "utf-8");
            httpCon.connect();
        } catch (IOException e) {
            Log.e(TAG, "Connection failed: could not realize the DELETE request");
        } finally {
            if(httpCon != null) {
                // FOR DEBUGGING ONLY -> PRINTING THE RESPONSE CODE


                BufferedReader br = null;
                try {
                    System.out.println("Response code: " + httpCon.getResponseCode());
                    br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                    String line, responseText = "";
                    while ((line = br.readLine()) != null) {
                        System.out.println("LINE: "+line);
                        responseText += line;
                    }
                    br.close();
                } catch (IOException e) {
                    Log.e(TAG, "INSIDE FIRST FINALLY CLAUSE: ERROR WITH IOEXCEPTION");
                }
                httpCon.disconnect();
            }
        }
        return null;
    }

    /**
     * Updates the current path (which consists only of the IP address and port number of the server)
     * with the device's ID.
     */
    private void updatePath() {
        int id = this.device.getDeviceId();
        this.path = this.path + "devices/" + id;
    }
}
