package hestia.backend.requests;

import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * This class does a POST request. It contains the Path to the Server and the Object to be send.
 * @see Request
 */

public class PostRequest extends Request {
    private JsonObject object;
    private String TAG = "PostRequest";

    public PostRequest(String path, JsonObject object) {
        super("POST", path);
        this.object = object;
    }

    @Override
    protected void setDoIO(HttpURLConnection connector) {
        connector.setDoOutput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection connector) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connector.getOutputStream());
        connector.setReadTimeout(2000);
        connector.setConnectTimeout(2000);
        outputStreamWriter.write(this.object.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
