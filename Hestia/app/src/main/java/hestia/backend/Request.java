package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;


/**
 * This super class will send a RESTful request to the Server.
 */

public class Request extends AsyncTask<Void, Void, Integer>{
    private final String TAG = "Request";
    private String requestType;
    private String path;
    private BackendInteractor backendInteractor;

    /**
     * Creates an instance of the Request class of the given type.
     * @param requestType the type of the RESTful request.
     * @param path the path of to send the request
     */
    public Request(String requestType, String path) {
        this.requestType = requestType;
        this.path = path;
        this.backendInteractor = BackendInteractor.getInstance();
    }

    /**
     * Send the DELETE request to the server
     * @param params parameters used for the background activity.
     * @return the response code of the DELETE request
     */
    @Override
    protected Integer doInBackground(Void... params) {
        Integer responseCode = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(this.path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);
            urlConnection.setRequestMethod(this.requestType);
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
        backendInteractor.updateDevices();
    }
}
