package hestia.backend.requests;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * This super class will send a HTTP request to the Server.
 */

abstract class Request extends AsyncTask<Void, Void, HttpURLConnection>{
    private String requestMethod;
    private String path;

    /**
     * Creates an instance of the Request class of the given type.
     * @param requestMethod the type of the HTTP request.
     * @param path the path of to send the request
     */
    Request(String requestMethod, String path) {
        this.requestMethod = requestMethod;
        this.path = path;
    }

    /**
     * Send the request to the server.
     * @return the result of the task. If the type T is Void, it will return a null.
     */
    @Override
    protected HttpURLConnection doInBackground(Void... params) {
        HttpURLConnection connector = null;
        final String TAG = "Request";
        try {
            URL url = new URL(this.path);
            connector = (HttpURLConnection) url.openConnection();
            connector.setReadTimeout(2000);
            connector.setConnectTimeout(2000);
            connector.setRequestMethod(this.requestMethod);
            connector.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            this.setDoIO(connector);
            connector.connect();
            this.performIOAction(connector);
            Log.d(TAG, "Response Code: " + String.valueOf(connector.getResponseCode()));
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "SocketTimeoutException");
            Log.e(TAG, e.toString());
        } catch (ConnectException e) {
            Log.e(TAG, "ConnectException");
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, "IOException: could not realize the request");
            Log.e(TAG, e.toString());
            e.printStackTrace();
        } finally {
            if (connector != null) {
                connector.disconnect();
            }
        }
        return connector;
    }

    /**
     * Depending on the type of request, the connector parameter must setDoInput or
     * setDoOutput to TRUE to allow I/O data transfer.
     * @param connector the connector between the Client and the Server
     */
    protected abstract void setDoIO(HttpURLConnection connector);

    /**
     * This method contains all the Input/Output data transfer. It will be used especially
     * for a GET request (Input) and for a POST request (Output).
     * @param connector the connector between the Client and the Server
     */
    protected abstract void performIOAction(HttpURLConnection connector) throws IOException;
}
