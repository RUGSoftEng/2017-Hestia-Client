package hestia.UIWidgets;

import android.view.View;
import android.widget.SeekBar;

public class HestiaSeekbar implements UIWidget {
    private int activatorId;
    private SeekBar activatorSeekBar;
    public HestiaSeekbar(int id, View v, int layoutId) {
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
