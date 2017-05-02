package hestia.backend.refactoring;

import android.util.Log;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import hestia.backend.BackendInteractor;

public class PostRequest extends Request {
    private final String TAG = "PostRequest";
    private String requiredInfo;

    public PostRequest(String path, String requiredInfo) {
        super("POST", path);
        this.requiredInfo = requiredInfo;
    }

    @Override
    protected void perform(HttpURLConnection urlConnection) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
        outputStreamWriter.write(this.requiredInfo);
        outputStreamWriter.flush();
        outputStreamWriter.close();

    }

    @Override
    protected void onPostExecute(HttpURLConnection urlConnection) {
        BackendInteractor.getInstance().updateDevices();
    }
}
