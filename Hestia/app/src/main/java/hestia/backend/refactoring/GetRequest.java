package hestia.backend.refactoring;

import java.net.HttpURLConnection;

public class GetRequest extends Request {

    public GetRequest(String requestType, String path) {
        super(requestType, path);
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {
        urlConnection.setDoInput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) {

    }
}
