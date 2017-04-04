package hestia.backend;

import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class will send a DELETE request to the Server in order to delete
 * the Device "device".
 */

public class RemoveDevice {
    final String TAG = "RemoveDevice";
    private String path;
    private Device device;

    /**
     * Creates an instance of the RemoveDevice class storing the path and the device passed as arguments.
     * @param path the path to the server. It contains the IP address and Port number.
     * @param device the device to be removed.
     */
    public RemoveDevice(String path, Device device) {
        this.path = path;
        this.device = device;
    }

    /**
     * Send the DELETE request to the server
     */
    public void sendDeleteRequest() {
        this.updatePath();
        // UPDATE PATH TO MATCH THE CORRECT DELETE ONE
        URL url = null;
        HttpURLConnection httpCon = null;
        try {
            url = new URL(path);
            httpCon = (HttpURLConnection) url.openConnection();
            //httpCon.setDoOutput(true);
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();
        } catch (IOException e) {
            Log.e(TAG, "Connection failed: could not realize the DELETE request");
        } finally {
            httpCon.disconnect();
        }
    }

    /**
     * Updates the current path (which consists only of the IP address and port number of the server)
     * with the device's ID.
     */
    private void updatePath() {
        int id = this.device.getDeviceId();
        this.path = this.path + "devices/" + id + "/";
    }
}
