package hestia.UI.activities.home.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import hestia.UI.activities.home.DeviceListFragment;
import hestia.UI.activities.home.ExpandableDeviceList;
import hestia.UI.elements.DeviceBar;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.Device;

public class DeviceTask extends AsyncTask<Object, String, ArrayList<Device>> {

    private final ServerCollectionsInteractor serverCollectionsInteractor;
    private final Context context;
    private final DeviceListFragment deviceListFragment;
    private final static String TAG = "DeviceTask";

    public DeviceTask(DeviceListFragment fragment) {
        serverCollectionsInteractor = fragment.getServerCollectionsInteractor();
        context = fragment.getContext();
        deviceListFragment = fragment;
    }

    @Override
    protected ArrayList<Device> doInBackground(Object... params) {
        ArrayList<Device> devices = new ArrayList<>();
        try {
            devices = serverCollectionsInteractor.getDevices();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            String exceptionMessage = "Could not connect to the server";
            publishProgress(exceptionMessage);
        } catch (ComFaultException comFaultException) {
            Log.e(TAG, comFaultException.toString());
            String error = comFaultException.getError();
            String message = comFaultException.getMessage();
            String exceptionMessage = error + ":" + message;
            publishProgress(exceptionMessage);
        }
        return devices;
    }

    @Override
    protected void onProgressUpdate(String... exceptionMessage) {
        Toast.makeText(context, exceptionMessage[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(ArrayList<Device> devices) {
        ArrayList<ArrayList<DeviceBar>> listDataChild = new ArrayList<>();
        for (Device device : devices) {
            Log.i(TAG, "device found");
            DeviceBar bar = new DeviceBar(deviceListFragment.getActivity().getSupportFragmentManager(), deviceListFragment.getActivity(), device, serverCollectionsInteractor);
            if (!listDataChild.contains(bar)) {
                if (!typeExists(device, listDataChild)) {
                    listDataChild.add(new ArrayList<DeviceBar>());
                    listDataChild.get(listDataChild.size() - 1).add(bar);
                } else {
                    listDataChild.get(getDeviceType(device, listDataChild)).add(bar);
                }
            }
        }
        ExpandableDeviceList changedAdapter = deviceListFragment.getListAdapter();
        changedAdapter.setListData(listDataChild);
        deviceListFragment.setListAdapter(changedAdapter);

        ExpandableListView changedListView = deviceListFragment.getExpListView();
        changedListView.setAdapter(deviceListFragment.getListAdapter());
        deviceListFragment.setExpListView(changedListView);

    }


    private boolean typeExists(Device device, ArrayList<ArrayList<DeviceBar>> listDataChild) {
        String deviceType = device.getType();
        for (ArrayList<DeviceBar> groupOfDevices : listDataChild) {
            Device checkDevice = groupOfDevices.get(0).getDevice();
            if (checkDevice.getType().equals(deviceType)) {
                return true;
            }
        }
        return false;
    }

    private int getDeviceType(Device device, ArrayList<ArrayList<DeviceBar>> listDataChild) {
        String deviceType = device.getType();
        for (ArrayList<DeviceBar> groupOfDevices : listDataChild) {
            Device checkDevice = groupOfDevices.get(0).getDevice();
            if (checkDevice.getType().equals(deviceType)) {
                return listDataChild.indexOf(groupOfDevices);
            }
        }
        return -1;
    }
}
