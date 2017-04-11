package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * This class will send a DELETE request to the Server in order to delete
 * the Device "device".
 */

public class RemoveDeviceTask extends AsyncTask<Void, Void, Integer> {
    final String TAG = "RemoveDeviceTask";
    private Device device;
    private ClientInteractionController cic;

    /**
     * Creates an instance of the RemoveDeviceTask class storing the path and the device passed as arguments.
     * @param device the device to be removed.
     */
    public RemoveDeviceTask(Device device) {
        this.device = device;
        this.cic = ClientInteractionController.getInstance();
    }

    /**
     * Send the DELETE request to the server
     */
    @Override
    protected Integer doInBackground(Void... params) {
        // Update the general path to match the one for the device to be deleted.
        int id = this.device.getDeviceId();
        String path = this.cic.getPath() + "devices/" + id;

        Integer responseCode = null;
        HttpURLConnection httpCon = null;
        try {
            URL url = new URL(path);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("DELETE");
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("charset", "utf-8");
            httpCon.connect();
            responseCode = httpCon.getResponseCode();
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "SocketTimeoutException");
            Log.e(TAG, e.toString());
        } catch (ConnectException e) {
            Log.e(TAG, "ConnectExeption");
            Log.e(TAG, e.toString());
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

    @Override
    protected void onPostExecute(Integer result) {
        cic.updateDevices();
    }
}
