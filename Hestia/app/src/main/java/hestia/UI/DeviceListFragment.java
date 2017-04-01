package hestia.UI;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import hestia.backend.ClientInteractionController;
import hestia.backend.Device;
import com.rugged.application.hestia.R;

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
    FloatingActionButton fab;

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
        Log.i(TAG, "Create the fragment view");
        fab = (FloatingActionButton)view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add device dialog
                Toast.makeText(getContext(), "Implement opening dialog to add device",
                        Toast.LENGTH_SHORT).show();
            }
        });
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();



        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        listAdapter = new ExpandableListAdapter(listDataHeader, listDataChild, getActivity(), c);

        expListView.setAdapter(listAdapter);

        c = new ClientInteractionController();

        ArrayList<Device> devices = c.getDevices();
        //Log.i(TAG, devices.size() + "");
        if(devices!=null) {
            for (Device d : devices) {
                if (!listDataHeader.contains(d.getType())) {
                    listDataHeader.add(d.getType());
                    listDataChild.put(d.getType(), new ArrayList<Device>());
                }
                //find corresponding header for the child
                listDataChild.get(d.getType()).add(d);
            }
        }


        return view;
    }


}
