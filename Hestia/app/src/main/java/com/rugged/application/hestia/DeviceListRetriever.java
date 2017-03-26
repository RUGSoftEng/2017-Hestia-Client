package com.rugged.application.hestia;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
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
class DeviceListRetriever extends AsyncTask<Void,Void,ArrayList<Device>> {
    String TAG = "DeviceListRetriever";
    String path;
    public DeviceListRetriever(String path) {
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
            url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.i(TAG, in.toString());
            devices = readStream(in);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            urlConnection.disconnect();
        }
/*
        //mock data
        ArrayList<Activator> activators = new ArrayList<>();
        activators.add(new Activator<>(0, false, "light_OnOROff", "TOGGLE"));
        ArrayList<Activator> a2 = new ArrayList<>();
        a2.add(new Activator<>(0, 0, "Lock_OnOROff", "SLIDER"));
        Device d1 = new Device(0, "Light 1", "Light", activators);
        Device d2 = new Device(0, "Light 2", "Light", activators);
        Device d3 = new Device(0, "lock 1", "Lock", a2);
        ArrayList<Device> devices = new ArrayList<>();
        devices.add(d1);
        devices.add(d2);
        devices.add(d3);

        // add header data
        /*
        for (Device d : devices) {
            if (!listDataHeader.contains(d.getType())) {
                listDataHeader.add(d.getType());
                listDataChild.put(d.getType(), new ArrayList<Device>());
            }
            //find corresponding header for the child
            listDataChild.get(d.getType()).add(d);
        }
//            listAdapter.notifyDataSetChanged();
        return devices;
    }*/
        return devices;
    }

    private ArrayList<Device> readStream(InputStream is) throws IOException {
        Gson gson= new Gson();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type deviceListType= new TypeToken<ArrayList<Device>>() {}.getType();
        ArrayList<Device> responseDev = gson.fromJson(gson.newJsonReader(reader), deviceListType);
        System.out.println(responseDev);
        reader.close();
        return responseDev;
    }
}
