package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Subclass of AsyncTask which is used to send a POST request to the server, containing data
 * about the device's activator, whose state is changed.
 */

public class StateModificationTask extends AsyncTask<Void,Integer,Integer> {
    private String TAG = "StateModificationTask";
    private ClientInteractionController cic;
    private int deviceId;
    private int activatorId;
    private ActivatorState newState;

    /**
     * Creates an instance of the StateModificationTask class, storing the device's Id,
     * the activator's id and the new state of the activator.
     * @param deviceId the id of the device
     * @param activatorId the id of the activator
     * @param newState the new state
     */
    public StateModificationTask(int deviceId, int activatorId, ActivatorState newState) {
        this.deviceId = deviceId;
        this.activatorId = activatorId;
        this.newState = newState;
        this.cic = ClientInteractionController.getInstance();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer response = null;
        String activatorPath = cic.getPath() + "devices/" + deviceId + "/activators/" + activatorId;
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(activatorPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            OutputStream deviceOutputStream = urlConnection.getOutputStream();
            writeStream(deviceOutputStream);
            response = urlConnection.getResponseCode();
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "SocketTimeoutException");
            Log.e(TAG, e.toString());
        } catch (ConnectException e) {
            Log.e(TAG, "ConnectExeption");
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }

    /**
     * Write the new state to the output stream, which is sent over the urlConnection.
     */
    private void writeStream(OutputStream os) throws IOException {
        JsonObject json = new JsonObject();
        JsonPrimitive jPrimitive = new JsonPrimitive(String.valueOf(newState));
        json.add("state", jPrimitive);
        Log.i(TAG,json.toString());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
        outputStreamWriter.write(json.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
