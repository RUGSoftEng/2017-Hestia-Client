package hestia.backend.refactoring;

import java.net.HttpURLConnection;

public class GetRequest extends Request {

    public GetRequest(String requestType, String path) {
        super(requestType, path);
    }

    @Override
    protected void perform(HttpURLConnection urlConnection) {

    }

    @Override
    protected void onPostExecute(HttpURLConnection urlConnection) {

    }
}
