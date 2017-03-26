package com.rugged.application.hestia;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;

public class ActivatorSeekbar implements UIWidget{
    int activatorId;
    SeekBar activatorSeekBar;
    public ActivatorSeekbar(int id, View v, int layoutId) {
        activatorId = id;
        activatorSeekBar = (SeekBar) v.findViewById(layoutId);
    }

    @Override
    public void setActivatorId(int id) {
        activatorId = id;
    }

    @Override
    public int getActivatorId() {
        return activatorId;
    }

    public SeekBar getActivatorSeekBar() {
        return activatorSeekBar;
    }
}
