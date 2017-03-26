package com.rugged.application.hestia;

import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class ActivatorSwitch implements UIWidget{
    private final static String TAG = "ActivatorSwitch";
    private int activatorId;
    private Switch activatorSwitch;

    public ActivatorSwitch(int id, View v, int layoutId) {
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
