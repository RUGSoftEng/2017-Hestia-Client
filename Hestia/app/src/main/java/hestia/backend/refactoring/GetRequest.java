package hestia.backend.refactoring;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import hestia.backend.Activator;
import hestia.backend.ActivatorDeserializer;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

public class GetRequest extends Request {
    private ArrayList<Device> devices;
    private final String TAG = "GetRequest";

    public GetRequest(String path) {
        super("GET", path);
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {
        urlConnection.setDoInput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Activator.class, new ActivatorDeserializer());
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Type deviceListType= new TypeToken<ArrayList<Device>>() {}.getType();
        this.devices = gson.fromJson(gson.newJsonReader(reader), deviceListType);
        reader.close();
    }

    @Override
    protected void onPostExecute(HttpURLConnection urlConnection) {
        if(devices != null) {
            BackendInteractor.getInstance().setDevices(devices);
        } else {
            Log.e(TAG, "DEVICES ARRAY WAS ABOUT TO BE SET TO NULL");
        }
    }
}
