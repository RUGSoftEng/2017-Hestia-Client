package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PostDeviceTask extends AsyncTask<Void, Void, Integer> {

    private final String TAG = "PostDeviceTask";
    private String path;
    private HashMap<String, String> deviceHashMap;
    private ClientInteractionController cic;

    public PostDeviceTask(HashMap<String, String> deviceHashMap) {
        this.deviceHashMap = deviceHashMap;
        this.cic = ClientInteractionController.getInstance();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        String postPath = this.cic.getPath() + "devices/";
        Integer response = null;
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(postPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            OutputStream deviceOutputStream = urlConnection.getOutputStream();
            writeStream(deviceOutputStream);
            response = urlConnection.getResponseCode();
            Log.i(TAG, "Response code: " + response);
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
     * Write the JSON for the new device to the output stream, which is sent over the urlConnection
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
