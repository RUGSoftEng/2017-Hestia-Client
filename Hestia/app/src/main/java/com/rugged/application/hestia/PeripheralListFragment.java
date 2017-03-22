package com.rugged.application.hestia;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                //inflate depending on toggle or slider
                convertView = infalInflater.inflate(R.layout.child_list_item, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.child_item_text);
                txtListChild.setText(childText);

            ImageView imageview = (ImageView) convertView.findViewById(R.id.imageview);

            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(getActivity(), view);
                    popup.getMenuInflater().inflate(R.menu.popup,
                            popup.getMenu());
                    if (childText.contains("SLIDER")) {
                        popup.getMenu().findItem(R.id.slide).setEnabled(true);
                    }
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.settings :
                                    //do something
                                    break;
                                case R.id.delete :
                                    //do something
                                    break;
                                case R.id.slide :
                                    //show notification
                                    final Dialog dialog = new Dialog(_context);
                                    dialog.setContentView(R.layout.slide_dialog);
                                    dialog.setTitle("Title...");
                                    final RelativeLayout layout = (RelativeLayout) dialog.
                                            findViewById(R.id.slide_dialog);
                                    //change color accordingly to slider

                                    // set the custom dialog components - text, image and button
                                    final Switch switchButton = (Switch) dialog.findViewById(R.id.switch_dialog);

                                    final SeekBar s = (SeekBar) dialog.findViewById(R.id.slide_seek_bar);
                                    s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                        @Override
                                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                            layout.setBackgroundColor(0xffffffff - seekBar.getProgress());
                                            if (seekBar.getProgress() > 0) {
                                                switchButton.setChecked(true);
                                            }else {
                                                switchButton.setChecked(false);
                                            }
                                        }

                                        @Override
                                        public void onStartTrackingTouch(SeekBar seekBar) {

                                        }

                                        @Override
                                        public void onStopTrackingTouch(SeekBar seekBar) {

                                        }
                                    });

                                    switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (b) {
                                                s.setProgress(s.getMax());
                                            } else {
                                                s.setProgress(0);
                                            }
                                        }
                                    });

                                    Button backButton = (Button) dialog.findViewById(R.id.back_button);
                                    backButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });

                                    Button confirmButton = (Button) dialog.findViewById(R.id.confirm_button);
                                    confirmButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //send data to the server
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();

                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                }
            });
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
                listDataChild.get(d.getType()).add(d.getName() + d.getActivators().get(0).getType());
//                Log.i(TAG, listDataChild.get(d.getType()).toString());

//                listDataChild.put(d.getType(), listDataChild.get(d.getType()).add(d.getName()));
            }
            listAdapter.notifyDataSetChanged();
            return null;

        }




    }


}
