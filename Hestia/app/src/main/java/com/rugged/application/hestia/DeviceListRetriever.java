package com.rugged.application.hestia;

import android.os.AsyncTask;

import java.util.ArrayList;


public class DeviceListRetriever extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... voids)  {
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
        }*/
//            listAdapter.notifyDataSetChanged();
        return null;
    }
}
