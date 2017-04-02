package hestia.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hestia.UIWidgets.HestiaSeekbar;
import hestia.UIWidgets.HestiaSwitch;
import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.ClientInteractionController;
import com.rugged.application.hestia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hestia.backend.Device;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Device>> listDataChild;
    private Context context;

    private ClientInteractionController c;
    private final static String TAG = "ExpandableList";

    public ExpandableListAdapter(List<String> listDataHeader,
                          HashMap<String, List<Device>> listChildData,
                          Context context) {
        Log.i(TAG, "Constructor");
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.context = context;
        this.c = ClientInteractionController.getInstance();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        Log.i(TAG, "I am being called");

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
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
                PopupMenu popup = new PopupMenu(context, view);
                popup.getMenuInflater().inflate(R.menu.popup,
                        popup.getMenu());

                for (int i = 0; i < getChildDevice(groupPosition, childPosition)
                        .getActivators().size(); i++) {
                    if (getChildDevice(groupPosition, childPosition).getActivators().get(i)
                            .getType().equals("SLIDER")) {
                        popup.getMenu().findItem(R.id.slide).setEnabled(true);
                        popup.getMenu().findItem(R.id.slide).setVisible(true);
                        break;
                    }
                }

                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.settings:
                                //Settings
                                break;
                            case R.id.delete:
                                //Remove list item
                                break;
                            case R.id.slide:
                                //show notification
//                                final SlideDialog dialog = new SlideDialog(context,
//                                        getChildDevice(groupPosition, childPosition));
//                                dialog.show();
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
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
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
            LayoutInflater infalInflater = (LayoutInflater) context
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

    public void setListData(List<String> listDataHeader, HashMap<String, List<Device>> listDataChild){
        this.listDataChild = listDataChild;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

//    private ArrayList<HestiaSwitch> retrieveSwitches(HashMap<String, List<Device>> data, View v) {
//        ArrayList<HestiaSwitch> switches = new ArrayList<>();
//        for (String key : data.keySet()) {
//            for (Device d : data.get(key)) {
//                //create the switch buttons here for each device
//                for (Activator a : d.getActivators())
//                if (a.getType().equals("LOCK")) {
//                    HestiaSwitch s = new HestiaSwitch(d, a.getId(), v, R.id.light_switch);
//                    s.getActivatorSwitch().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                    switches.add(s);
//                }
//            }
//        }
//        return switches;
//    }
//
//    private ArrayList<HestiaSeekbar> retrieveSeekbars(HashMap<String, List<Device>> data, View v) {
//        ArrayList<HestiaSeekbar> seekbars = new ArrayList<>();
//        for (String key : data.keySet()) {
//            for (Device d : data.get(key)) {
//                //create the switch buttons here for each device
//                for (Activator a : d.getActivators())
//                    if (a.getType().equals("TOGGLE")) {
//                        HestiaSeekbar s = new HestiaSeekbar(d, a.getId(), v, R.id.light_switch);
//                        s.getActivatorSeekBar().setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        });
//                        seekbars.add(s);
//                    }
//            }
//        }
//        return seekbars;
//    }

}
//
//    Device d0 = getChildDevice(groupPosition, childPosition);
//
//    Activator a0 = null;
//        for (Activator activator : d0.getActivators()) {
//                a0 = activator;
//                Log.i(TAG, "ID of the current activators: " + activator.getId());
//                if (activator.getState().getType().equals("TOGGLE")) {
//                a0 = activator;
//                break;
//                }
//                }
//
//                if (a0 != null) {
//final HestiaSwitch hestiaSwitch = new HestiaSwitch(d0, a0, convertView,
//        R.id.light_switch);
//        hestiaSwitch.getActivatorSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//@Override
//public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        Log.i(TAG, "I am being clicked");
//        Activator a = hestiaSwitch.getActivatorId();
//        Device d = hestiaSwitch.getDevice();
//        int activatorId = a.getId() - 1;
//        Log.i(TAG, "ID: " + activatorId);
//        Log.i(TAG, "activator size: " + d.getActivators().size());
//        Log.i(TAG, "type: " + a.getState().getType());
//
//        ActivatorState state = a.getState();
//        if(b){
//        // True
//        state.setState(true);
//        c.setActivatorState(d,activatorId,state);
//        Log.i(TAG, "I Am being set to true");
//        }else{
//        // False
//        state.setState(false);
//        c.setActivatorState(d,activatorId,state);
//        Log.i(TAG, "I Am being set to false");
//        }
//        }
//        });
//        }
//
//
