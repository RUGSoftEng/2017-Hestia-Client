package hestia.backend.refactoring;

import java.io.IOException;
import java.net.HttpURLConnection;

public class DeleteRequest extends Request {

    public DeleteRequest(String path) {
        super("DELETE", path);
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {

    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) throws IOException {

    }
}
