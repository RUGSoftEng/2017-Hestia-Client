package hestia.backend.refactoring;

import com.google.gson.GsonBuilder;
import java.net.HttpURLConnection;
import java.util.HashMap;


public class GetPluginsRequest extends GetRequest<HashMap<String, String>> {

    public GetPluginsRequest(String path) {
        super(path);
    }

    @Override
    protected void registerTypeAdapter(GsonBuilder gsonBuilder) {

    }

    @Override
    protected void onPostExecute(HttpURLConnection urlConnection) {

    }
}
