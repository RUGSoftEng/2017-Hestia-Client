package hestia.UIWidgets;

import android.view.View;
import android.widget.Switch;

public class HestiaSwitch implements UIWidget {
    private final static String TAG = "HestiaSwitch";
    private int activatorId;
    private Switch activatorSwitch;

    public HestiaSwitch(int id, View v, int layoutId) {
        activatorId = id;
        activatorSwitch = (Switch) v.findViewById(layoutId);
    }

    @Override
    public void setActivatorId(int id) {
        activatorId = id;
    }

    @Override
    public int getActivatorId() {
        return activatorId;
    }

    public Switch getActivatorSwitch() {
        return activatorSwitch;
    }
}
