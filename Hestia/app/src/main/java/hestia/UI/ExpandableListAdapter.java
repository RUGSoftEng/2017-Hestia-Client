package hestia.UI;

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

import hestia.backend.ClientInteractionController;
import com.rugged.application.hestia.R;

import java.util.ArrayList;

import hestia.backend.Device;

/**
 * The ExpendableListAdapter creates the expendable list. It gets an arraylist with deviceBars
 * and adds them to the Expendable List. It also checks which options should be visible, and
 * adds onClickListeners to these options in the popup menu.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private ArrayList<ArrayList<DeviceBar>> listDataChild;
    private Context context;
    private ClientInteractionController c;

    public ExpandableListAdapter(ArrayList<ArrayList<DeviceBar>> listChildData, Context context) {
        this.listDataChild = listChildData;
        this.context = context;
        this.c = ClientInteractionController.getInstance();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final DeviceBar dBar = (DeviceBar) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_list_item, parent);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.child_item_text);
        txtListChild.setText(dBar.getDevice().getName());

        ImageView imageview = (ImageView) convertView.findViewById(R.id.imageview);

        Boolean state = Boolean.parseBoolean(dBar.getDevice().getActivator(0).getState().toString());
        dBar.setLayout(convertView, R.id.light_switch,state);

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Device d = ((DeviceBar) getChild(groupPosition, childPosition)).getDevice();
                PopupMenu popup = createPopupMenu(view,d);

                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.sliders:
                                new SlideDialog(context,d.getSliders(),d).show();
                                break;
                            case R.id.delete:
                                c.deleteDevice(d);
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
        return this.listDataChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataChild.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataChild.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        DeviceBar dBar = ((ArrayList<DeviceBar>) getGroup(groupPosition)).get(0);
        String headerTitle = dBar.getDevice().getType();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.list_group, parent);
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

    public void setListData(ArrayList<ArrayList<DeviceBar>> listDataChild){
        this.listDataChild = listDataChild;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private PopupMenu createPopupMenu(View view, Device d){
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.popup,
                popup.getMenu());

        if (d.getSliders()==null) {
            popup.getMenu().findItem(R.id.sliders).setEnabled(false);
            popup.getMenu().findItem(R.id.sliders).setVisible(false);
        }
        return popup;
    }
}