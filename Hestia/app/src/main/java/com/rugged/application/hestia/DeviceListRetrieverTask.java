package com.rugged.application.hestia;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * This performs the task of getting the devices. It runs in the background as an AsyncTask
 * @see android.os.AsyncTask
 */
class DeviceListRetrieverTask extends AsyncTask<Void,Void,ArrayList<Device>> {
    String TAG = "DeviceListRetrieverTask";
    String path;
    public DeviceListRetrieverTask(String path) {
        this.path = path;
    }

    /**
     * This method runs in the background of the app looking for the devices.
     * @return an ArrayList containing the devices known to the server
     */
    @Override
    protected ArrayList<Device> doInBackground(Void... voids) {
        String devicesPath = path + "devices/";
        URL url = null;
        HttpURLConnection urlConnection = null;
        ArrayList<Device> devices = null;
        try {
            url = new URL(devicesPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.i(TAG, in.toString());

            devices = readStream(in);
            StringBuilder sb = new StringBuilder();
            for (Device d : devices) {
                sb.append(d.toString());
            }
            Log.i(TAG, sb.toString());
            urlConnection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        return devices;
    }

    private ArrayList<Device> readStream(InputStream is) throws IOException {
        GsonBuilder gsonB = new GsonBuilder();
        gsonB.registerTypeAdapter(Activator.class, new ActivatorDeserializer());
        Gson gson = gsonB.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type deviceListType= new TypeToken<ArrayList<Device>>() {}.getType();
        ArrayList<Device> responseDev = gson.fromJson(gson.newJsonReader(reader), deviceListType);
        System.out.println(responseDev);
        reader.close();
        return responseDev;
    }
}
