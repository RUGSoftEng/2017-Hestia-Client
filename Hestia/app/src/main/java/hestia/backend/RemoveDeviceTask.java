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
    private URL url;
    private HttpURLConnection httpCon;

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
        // Update the general path to match the one for the device to be deleted.
        int id = this.device.getDeviceId();
        this.path = this.path + "devices/" + id;

        try {
            this.url = new URL(this.path);
            this.httpCon = (HttpURLConnection) url.openConnection();
            this.httpCon.setRequestMethod("DELETE");
            this.httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.httpCon.setRequestProperty("charset", "utf-8");
            this.httpCon.connect();
        } catch (IOException e) {
            Log.e(TAG, "Connection failed: could not realize the DELETE request");
        } finally {
            if(this.httpCon != null) {
                // FOR DEBUGGING ONLY -> PRINTING THE RESPONSE CODE
                this.logResponse();
                this.httpCon.disconnect();
            }
        }
        return null;
    }

    /**
     *  Logs the response received from the server after sending the DELETE request.
     */
    private void logResponse() {
        BufferedReader br;
        try {
            Log.i(TAG, "Response code: " + this.httpCon.getResponseCode());
            br = new BufferedReader(new InputStreamReader(this.httpCon.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Log.i(TAG, "LINE: "+line);
            }
            br.close();
        } catch (IOException e) {
            Log.e(TAG, "RESPONSE CANNOT BE REACHED");
        }
    }
}
