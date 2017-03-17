package com.rugged.application.hestia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//fragment taking care of the list UI
public class PeripheralListFragment extends Fragment {

    private final static String TAG = "PeripheralListFragment";

    private RecyclerView myPeripheralRecyclerView;
    private PeripheralAdapter mAdapter;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_peripheral_list, container, false);

//        myPeripheralRecyclerView = (RecyclerView) view.findViewById(R.id.peripheral_recycler_view);
//        myPeripheralRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

//        updateUI();

        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Lock");
        listDataHeader.add("Light");
//        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> lock = new ArrayList<String>();
        lock.add("Lock 1");
        lock.add("Lock 2");

        List<String> light = new ArrayList<String>();
        light.add("Light 1");
        light.add("Light 2");

//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//        comingSoon.add("The Smurfs 2");
//        comingSoon.add("The Spectacular Now");
//        comingSoon.add("The Canyons");
//        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), lock); // Header, Child data
        listDataChild.put(listDataHeader.get(1), light);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    private class PeripheralHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public TextView mTitleTextView;
        private Peripheral mPeripheral;

        public PeripheralHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView;
        }
        public void bindPeripheral(Peripheral p) {
            mPeripheral = p;
            mTitleTextView.setText(mPeripheral.getId() + ". " + mPeripheral.getType());
        }

        @Override
        public void onClick(View view) {
            Intent intent = PeripheralActivity.newIntent(getActivity(), mPeripheral.getId());
            startActivity(intent);
        }
    }

    private class PeripheralAdapter extends RecyclerView.Adapter<PeripheralHolder> {

        private List<Peripheral> mPeripherals;

        public PeripheralAdapter(List<Peripheral> peripherals){
            mPeripherals = peripherals;
        }

        @Override
        public PeripheralHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //simple_list_item can be changed to custom item layout (page 188 of book)
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new PeripheralHolder(view);
        }

        @Override
        public void onBindViewHolder(PeripheralHolder holder, int position) {
            Peripheral peripheral = mPeripherals.get(position);
            holder.bindPeripheral(peripheral);
        }

        @Override
        public int getItemCount() {
            return mPeripherals.size();
        }
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
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
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

    private void updateUI() {
        PeripheralLab peripheralLab = PeripheralLab.get(getActivity());
        List<Peripheral> peripherals = peripheralLab.getPeripherals();

        mAdapter = new PeripheralAdapter(peripherals);
        myPeripheralRecyclerView.setAdapter(mAdapter);
    }
}
