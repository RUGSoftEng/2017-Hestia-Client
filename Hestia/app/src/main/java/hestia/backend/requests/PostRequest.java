package hestia.backend.requests;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PostRequest extends Request {
    private final String TAG = "PostRequest";
    private String object;

    public PostRequest(String path, String object) {
        super("POST", path);
        this.object = object;
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {
        urlConnection.setDoOutput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
        outputStreamWriter.write(this.object);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
