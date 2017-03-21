package com.rugged.application.hestia;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PeripheralListFragment extends Fragment {

    private final static String TAG = "PeripheralListFragment";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peripheral_list, container, false);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);


        //request the list
        new RetrievePeripheralList().execute();



        // setting list adapter
        return view;
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ArrayList<Activator> activators = new ArrayList<>();
            activators.add(new Activator(0, false, "light_OnOROff", "TOGGLE"));

//                Log.i(TAG, listDataChild.get(d.getType()).toString());

//                listDataChild.put(d.getType(), listDataChild.get(d.getType()).add(d.getName()));




            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                //inflate depending on toggle or slider
                convertView = infalInflater.inflate(R.layout.list_item_slider, null);
                Log.i(TAG, "called\t" + (convertView == null));

//                if (childText.contains("SLIDER")) {
//                    convertView = infalInflater.inflate(R.layout.list_item_slider, null);
//                    Log.i(TAG, "I am being called for the convertView");
//                } else {
//
//                }
            }
            ViewSwitcher switcher = (ViewSwitcher) convertView.findViewById(R.id.my_switcher);
            if (childText.contains("Toggle")) {
                switcher.showNext(); //or switcher.showPrevious();
            }
            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem_slider);
                txtListChild.setText(childText);
//


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Go to the listitem
                    Toast.makeText(getActivity(), "Clicked group: " + groupPosition + ", childpos: "
                            + childPosition, Toast.LENGTH_SHORT).show();
                }
            });
            if (childText.contains("Toggle")) {
                switcher.showPrevious(); //or switcher.showPrevious();
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private class RetrievePeripheralList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //retrieve the Devices;
            ArrayList<Activator> activators = new ArrayList<>();
            activators.add(new Activator(0, false, "light_OnOROff", "TOGGLE"));
            ArrayList<Activator> a2 = new ArrayList<>();
            a2.add(new Activator(0, false, "Lock_OnOROff", "SLIDER"));
            Device d1 = new Device(0, "Light 1", "Light", activators);
            Device d2 = new Device(0, "Light 2", "Light", activators);
            Device d3 = new Device(0, "lock 1", "Lock", a2);
            ArrayList<Device> devices = new ArrayList<>();
            devices.add(d1);
            devices.add(d2);
            devices.add(d3);



            /**
             * TODO: retrieve the headers, although it would be possible to retrieve a header and
             * then add its child data
             */

            // add header data
            for (Device d : devices) {
                if (!listDataHeader.contains(d.getType())) {
                    listDataHeader.add(d.getType());
                    listDataChild.put(d.getType(), new ArrayList<String>());
                }
                //find corresponding header for the child
                listDataChild.get(d.getType()).add(d.getName() + " " +
                        d.getActivators().get(0).getType());
//                Log.i(TAG, listDataChild.get(d.getType()).toString());

//                listDataChild.put(d.getType(), listDataChild.get(d.getType()).add(d.getName()));
            }
            listAdapter.notifyDataSetChanged();


                return null;

        }




    }


}
