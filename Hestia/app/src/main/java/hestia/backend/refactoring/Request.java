package hestia.backend.refactoring;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import hestia.backend.BackendInteractor;


/**
 * This super class will send a RESTful request to the Server.
 */

public abstract class Request<T> extends AsyncTask<Void, Void, T>{
    private final String TAG = "Request";
    private String requestType;
    private String path;

    /**
     * Creates an instance of the Request class of the given type.
     * @param requestType the type of the RESTful request.
     * @param path the path of to send the request
     */
    public Request(String requestType, String path) {
        this.requestType = requestType;
        this.path = path;
    }

    /**
     * Send the request to the server.
     * @return the result of the task. If the type T is Void, it will return a null.
     */
    @Override
    protected T doInBackground(Void... params) {
        T result = null;
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
            Log.d(TAG, String.valueOf(urlConnection.getResponseCode()));
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
        return result;
    }

    /**
     * Once the task is finished, it updates the list of devices.
     * @param result the result of the task.
     */
    @Override
    protected abstract void onPostExecute(T result);
}
