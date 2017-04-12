package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;

/**
 * Sends a POST request to the server with a JSON object containing
 * the data of the device to be added. Initially, the data is stored in a HashMap,
 * which will be converted in a JSON object.
 */

public class PostDeviceTask extends AsyncTask<Void, Void, Integer> {

    private final String TAG = "PostDeviceTask";
    private HashMap<String, String> deviceHashMap;
    private ClientInteractionController cic;

    /**
     * Creates an instance of the PostDeviceTask class with the HashMap.
     * @param deviceHashMap the device to be added
     */
    public PostDeviceTask(HashMap<String, String> deviceHashMap) {
        this.deviceHashMap = deviceHashMap;
        this.cic = ClientInteractionController.getInstance();
    }

    /**
     * Runs as a thread in the background. Tries to establish a connection with the server and then
     * invokes the writeStream method to perform the actual POST.
     * @return the HTTP response code of the POST.
     */
    @Override
    protected Integer doInBackground(Void... params) {
        String postPath = this.cic.getPath() + "devices/";
        Integer response = null;
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(postPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            OutputStream deviceOutputStream = urlConnection.getOutputStream();
            writeStream(deviceOutputStream);
            response = urlConnection.getResponseCode();
            Log.i(TAG, "Response code: " + response);
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "SocketTimeoutException");
            Log.e(TAG, e.toString());
        } catch (ConnectException e) {
            Log.e(TAG, "ConnectExeption");
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Connection failed: could not realize the POST request for adding a new device");
            Log.e(TAG, e.toString());
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }

    /**
     * At the end of the task, updates the list of deviecs.
     * @param result holds the response code of the POST request
     */
    @Override
    protected void onPostExecute(Integer result) {
        cic.updateDevices();
    }

    /**
     * Write the JSON for the new device to the output stream, which is sent over the urlConnection.
     */
    private void writeStream(OutputStream os) throws IOException {
        JsonObject requiredInfo = new JsonObject();
        for(String key : this.deviceHashMap.keySet()) {
            String value = this.deviceHashMap.get(key);
            requiredInfo.addProperty(key, value);
        }
        JsonObject json = new JsonObject();
        json.addProperty("required_info", requiredInfo.toString());
        Log.i(TAG, "JSON created" + json.toString());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
        outputStreamWriter.write(json.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
