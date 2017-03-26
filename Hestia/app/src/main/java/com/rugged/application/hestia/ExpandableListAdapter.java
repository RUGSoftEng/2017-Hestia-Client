package com.rugged.application.hestia;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Device>> listDataChild;
    private Context context;
    private int activatorId;

    public ExpandableListAdapter(List<String> listDataHeader,
                          HashMap<String, List<Device>> listChildData,
                          Context context) {
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon).getName();
    }

    public Device getChildDevice(int groupPostition, int childPostion) {
        return this.listDataChild.get(this.listDataHeader.get(groupPostition))
                .get(childPostion);
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
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //inflate depending on toggle or slider
            convertView = infalInflater.inflate(R.layout.child_list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.child_item_text);
        txtListChild.setText(childText);

        ActivatorSwitch activatorSwitch = new ActivatorSwitch(new Random().nextInt(4), convertView,
                R.id.light_switch);
        activatorSwitch.getActivatorSwitch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send state change to the server
            }
        });

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
                                final SlideDialog dialog = new SlideDialog(context,
                                        getChildDevice(groupPosition, childPosition));
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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

