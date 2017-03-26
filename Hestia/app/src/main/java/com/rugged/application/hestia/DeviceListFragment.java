package com.rugged.application.hestia;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This fragment takes care of generating the list of peripherals on the phone. It sends an HTTP
 * GET request to the server to populate the device list.
 *
 * @see DeviceListActivity
 */
public class DeviceListFragment extends Fragment {

    private final static String TAG = "DeviceListFragment";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Device>> listDataChild;

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
        View view = inflater.inflate(R.layout.fragment_peripheral_list, container, false);

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listAdapter = new ExpandableListAdapter(listDataHeader, listDataChild, getActivity());

        expListView.setAdapter(listAdapter);

        //request the list
        new DeviceListRetriever().execute();

        // setting list adapter
        return view;
    }

    private class DeviceListRetriever extends AsyncTask<Void,Void,Void> {
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

            for (Device d : devices) {
                if (!listDataHeader.contains(d.getType())) {
                    listDataHeader.add(d.getType());
                    listDataChild.put(d.getType(), new ArrayList<Device>());
                }
                //find corresponding header for the child
                listDataChild.get(d.getType()).add(d);
            }
//            listAdapter.notifyDataSetChanged();
            return null;
        }
    }
}
