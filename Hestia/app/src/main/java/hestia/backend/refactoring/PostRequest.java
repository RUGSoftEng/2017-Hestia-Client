package hestia.backend.refactoring;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PostRequest extends Request {
    private final String TAG = "PostRequest";
    private String requiredInfo;

    public PostRequest(String path, String requiredInfo) {
        super("POST", path);
        this.requiredInfo = requiredInfo;
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {
        urlConnection.setDoOutput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
        outputStreamWriter.write(this.requiredInfo);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

}
