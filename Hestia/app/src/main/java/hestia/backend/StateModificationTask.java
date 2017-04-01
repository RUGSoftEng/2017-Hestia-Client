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


class StateModificationTask extends AsyncTask<Void,Integer,Integer> {
    private static final int HTML_ERROR_CODE = 300;
    private String TAG = "StateModificationTask";
    private ClientInteractionController cic;

    int deviceId;
    int activatorId;
    ActivatorState newState;
    String path;

    public StateModificationTask(int deviceId, int activatorId, ActivatorState newState,
                                 ClientInteractionController cic) {
        this.deviceId = deviceId;
        this.activatorId = activatorId;
        this.newState = newState;
        //this.path = cic.getPath();
        this.path = "http://" + cic.getIp() + ":" + cic.getPort() + "/";
        this.cic = cic;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer response = null;
        String activatorPath = path + "devices/" + deviceId + "/activator/" + activatorId;
        URL url = null;
        HttpURLConnection urlConnection = null;
        /*
         * We try posting to the server until we get a response code which does not signify an
         * error.
         */
        do {
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
            }
        }while(response > HTML_ERROR_CODE);
        // Currently, we are not externally handling the HTML response code.
        return response;
    }

    /**
     * Write the new state to the output stream, which is sent over the urlConnection
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
