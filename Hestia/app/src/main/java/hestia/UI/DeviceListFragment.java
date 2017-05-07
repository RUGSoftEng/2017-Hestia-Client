package hestia.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import hestia.backend.Activator;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;
import hestia.backend.DevicesChangeListener;
import hestia.backend.DevicesEvent;
import com.rugged.application.hestia.R;
import java.util.ArrayList;

/**
 * This fragment takes care of generating the list of peripherals on the phone. It sends an HTTP
 * GET request to the server to populate the device list.
 *
 * @see DeviceListActivity
 */
public class DeviceListFragment extends Fragment implements DevicesChangeListener{


    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private ArrayList<ArrayList<DeviceBar>> listDataChild;
    private BackendInteractor backendInteractor;
    private FloatingActionButton floatingActionButton;

    /**
     *
     * @param inflater The layout inflater used to generate the layout hierarchy
     * @param container The viewgroup with which the layout is instantiated
     * @return A view of an expandable list linked to the listDataHeader and listDataChild
     *         variables. Filling these lists will generate the GUI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View deviceListView = inflater.inflate(R.layout.fragment_device_list, container, false);
        createFloatingButton(deviceListView);

        listDataChild = new ArrayList<>();
        expListView = (ExpandableListView) deviceListView.findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(listDataChild, getActivity());

        expListView.setAdapter(listAdapter);

        populateUI();

        backendInteractor.addDevicesChangeListener(this);

        return deviceListView;
    }

    private void populateUI() {
        listDataChild = new ArrayList<>();
        backendInteractor = BackendInteractor.getInstance();
        ArrayList<Device> devices = backendInteractor.getDevices();
        for (Device device : devices) {
            Activator activator = device.getActivator(0);
            HestiaSwitch hestiaSwitch = new HestiaSwitch(device, activator, getActivity());
            DeviceBar bar = new DeviceBar(device, hestiaSwitch);
            if(!listDataChild.contains(bar)) {
                if (!typeExists(device)) {
                    listDataChild.add(new ArrayList<DeviceBar>());
                    listDataChild.get(listDataChild.size() - 1).add(bar);
                } else {
                    listDataChild.get(getDeviceType(device)).add(bar);
                }
            }

        }
        listAdapter.setListData(listDataChild);
        expListView.setAdapter(listAdapter);
    }

    @Override
    public void changeEventReceived(DevicesEvent evt) {
        populateUI();
    }

    private boolean typeExists(Device device) {
        String deviceType = device.getType();
        for (int i = 0; i < listDataChild.size(); i++) {
            Device checkDevice = listDataChild.get(i).get(0).getDevice();
            if (checkDevice.getType().equals(deviceType)) {
                return true;
            }
        }
        return false;
    }

    private int getDeviceType(Device device) {
        String deviceType = device.getType();
        for (int i = 0; i < listDataChild.size(); i++) {
            Device checkDevice = listDataChild.get(i).get(0).getDevice();
            if (checkDevice.getType().equals(deviceType)) {
                return i;
            }
        }
        return -1;
    }

    private void createFloatingButton(View view) {
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDeviceDialog(getActivity()).show();
            }
        });
    }
}
