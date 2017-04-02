package hestia.UIWidgets;

import android.view.View;
import android.widget.SeekBar;

import hestia.backend.Activator;
import hestia.backend.Device;

public class HestiaSeekbar implements UIWidget {
    private Activator a;
    private SeekBar activatorSeekBar;
    private Device d;

    public HestiaSeekbar(Device d, Activator a, View v, int layoutId) {
        this.a = a;
        activatorSeekBar = (SeekBar) v.findViewById(layoutId);
        this.d = d;
    }


    @Override
    public Device getDevice() {
        return d;

    }

    @Override
    public void setActivatorId(Activator a) {
        this.a = a;
    }

    @Override
    public Activator getActivatorId() {
        return a;
    }

    public SeekBar getActivatorSeekBar() {
        return activatorSeekBar;
    }
}
