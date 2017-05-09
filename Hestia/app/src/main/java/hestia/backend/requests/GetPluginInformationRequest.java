package hestia.backend.requests;

import android.app.Activity;
import android.widget.Toast;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import hestia.UI.AddDeviceInfo;

/**
 * This class will do a GET request, expecting a HashMap containing the information
 * of the selected Plugin.
 * @see GetRequest
 */

public class GetPluginInformationRequest extends GetRequest<HashMap<String, String>> {
    private Activity activity;

    public GetPluginInformationRequest(String path, Activity activity) {
        super(path);
        this.activity = activity;
    }

    @Override
    protected void setRegisterTypeAdapter(GsonBuilder gsonBuilder) {}

    @Override
    protected Type getReturnType() {
        return new TypeToken<HashMap<String, String>>(){}.getType();
    }

    /**
     * When the task is over, if the HashMap is complete, it will create an AddDeviceInfo window.
     * Otherwise, it will show an error message on the screen via Toast.
     * @param connector The connector between the Client and the Server.
     * @see AddDeviceInfo
     */
    @Override
    protected void onPostExecute(HttpURLConnection connector) {
        HashMap<String, String> plugins = super.getReturnValue();
        if(plugins != null) {
            new AddDeviceInfo(this.activity, plugins).show();
        } else {
            Toast.makeText(this.activity, "Wrong Input", Toast.LENGTH_SHORT).show();
        }
    }
}
