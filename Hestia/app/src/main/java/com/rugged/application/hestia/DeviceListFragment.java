package com.rugged.application.hestia;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
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
    ClientInteractionController c;

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
        c = new ClientInteractionController("192.168.178.26:5000/");
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        ArrayList<Device> devices = c.getDevices();
        Log.i(TAG, devices.size() + "");

        for (Device d : devices) {
            if (!listDataHeader.contains(d.getType())) {
                listDataHeader.add(d.getType());
                listDataChild.put(d.getType(), new ArrayList<Device>());
            }
            //find corresponding header for the child
            listDataChild.get(d.getType()).add(d);
        }

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        listAdapter = new ExpandableListAdapter(listDataHeader, listDataChild, getActivity());

        expListView.setAdapter(listAdapter);

        return view;
    }


}
