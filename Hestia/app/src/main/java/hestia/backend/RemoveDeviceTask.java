package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * This class will send a DELETE request to the Server in order to delete the target device.
 */

public class RemoveDeviceTask extends AsyncTask<Void, Void, Integer> {
    private final String TAG = "RemoveDeviceTask";
    private final Integer DELETE_SUCCESSFUL_CODE = 204;
    private Device device;
    private ClientInteractionController cic;

    /**
     * Creates an instance of the RemoveDeviceTask class with the device to be removed.
     * @param device the device to be removed.
     */
    public RemoveDeviceTask(Device device) {
        this.device = device;
        this.cic = ClientInteractionController.getInstance();
    }

    /**
     * Send the DELETE request to the server
     * @param params parameters used for the background activity.
     * @return the response code of the DELETE request
     */
    @Override
    public Integer doInBackground(Void... params) {
        // Update the general path to match the one for the device to be deleted.
        int id = this.device.getDeviceId();
        String path = this.cic.getPath() + "devices/" + id;

        Integer responseCode = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            responseCode = urlConnection.getResponseCode();
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
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return responseCode;
    }

    /**
     * Once the task is finished, it updates the list of devices.
     * @param result the response code
     */
    @Override
    protected void onPostExecute(Integer result) {
        cic.updateDevices();
    }
}
