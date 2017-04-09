package hestia.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This performs the task of getting the devices. It runs in the background as an AsyncTask
 * @see android.os.AsyncTask
 */
public class DeviceListRetrieverTask extends AsyncTask<Void,Void,ArrayList<Device>> {
    private static final String TAG = "DeviceListRetrieverTask";
    private ClientInteractionController cic;

    public DeviceListRetrieverTask() {
        this.cic = ClientInteractionController.getInstance();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * This method runs in the background of the app looking for the devices.
     * @return an ArrayList containing the devices known to the server
     */
    @Override
    protected ArrayList<Device> doInBackground(Void... voids) {

        String devicesPath = this.cic.getPath() + "devices/";
        URL url = null;
        HttpURLConnection urlConnection = null;
        ArrayList<Device> devices = null;
        try {
            url = new URL(devicesPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            devices = readStream(input);
        } catch(SocketTimeoutException e) {
            Log.e(TAG, "TimeoutException");
            Log.e(TAG, e.toString());
        } catch(ConnectException e) {
            Log.e(TAG, "ConnectExeption");
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return devices;
    }

    @Override
    protected void onPostExecute(ArrayList<Device> d) {
        super.onPostExecute(d);
        Log.i(TAG, "FINISHED EXECUTING");
        cic.setDevices(d);
    }

    private ArrayList<Device> readStream(InputStream is) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Activator.class, new ActivatorDeserializer());
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type deviceListType= new TypeToken<ArrayList<Device>>() {}.getType();
        ArrayList<Device> responseDev = gson.fromJson(gson.newJsonReader(reader), deviceListType);
        Log.i(TAG, "ResponseDev: " + (responseDev != null ? responseDev.toString() : "NULL"));
        reader.close();
        return responseDev;
    }
}
