package hestia.backend.requests;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * This class does a POST request. It contains the Path to the Server and the Object to be send.
 * @see Request
 */

public class PostRequest extends Request {
    private String object;

    public PostRequest(String path, String object) {
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
        outputStreamWriter.write(this.object);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
