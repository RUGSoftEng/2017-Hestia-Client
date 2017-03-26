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


class StateModificationTask extends AsyncTask<Void,Void,Integer> {
    private String TAG = "StateModificationTask";
    int deviceId;
    int activatorId;
    ActivatorState newState;
    String path;

    public StateModificationTask(int devId, int actId, ActivatorState newState, String path) {
        this.deviceId = devId;
        this.activatorId = actId;
        this.newState = newState;
        this.path = path;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer response = null;
        String activatorPath = path + "devices/" + deviceId + "/activator/" + activatorId;
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(activatorPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            OutputStream dos = urlConnection.getOutputStream();
            writeStream(dos);
            response = urlConnection.getResponseCode();
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }
        return response;
    }

    /**
     * Write the new state to the output stream, which is sent over the urlConnection
     * @param os
     */
    private void writeStream(OutputStream os) throws IOException {
        JsonObject json = new JsonObject();
        JsonPrimitive jp = new JsonPrimitive(String.valueOf(newState));
        json.add("state", jp);
        Log.i(TAG,json.toString());
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(json.toString());
        osw.flush();
        osw.close();
    }
}
