package hestia.backend.requests;

import android.app.Activity;
import android.widget.Toast;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import hestia.UI.AddDeviceInfo;

public class GetPluginsRequest extends GetRequest<HashMap<String, String>> {
    private final String TAG = "GetPluginRequest";
    private Activity activity;

    public GetPluginsRequest(String path, Activity activity) {
        super(path);
        this.activity = activity;
    }

    @Override
    protected void registerTypeAdapter(GsonBuilder gsonBuilder) {}

    @Override
    protected Type getReturnType() {
        return new TypeToken<HashMap<String, String>>(){}.getType();
    }

    @Override
    protected void onPostExecute(HttpURLConnection urlConnection) {
        HashMap<String, String> plugins = super.getReturnValue();
        if(plugins != null) {
            new AddDeviceInfo(this.activity, plugins).show();
        } else {
            Toast.makeText(this.activity, "Wrong Input", Toast.LENGTH_SHORT).show();
        }
    }
}
