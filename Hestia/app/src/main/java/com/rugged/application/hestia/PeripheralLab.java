package com.rugged.application.hestia;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeripheralLab {
    private static PeripheralLab sPeripheralLab;
    private final static String TAG = "PeripheralLab";

    private List<Peripheral> mPeripherals;

    public static PeripheralLab get(Context context) {
        if (sPeripheralLab == null) {
            sPeripheralLab = new PeripheralLab(context);
        }
        return sPeripheralLab;
    }

    private PeripheralLab(Context context) {
        mPeripherals = new ArrayList<>();
        Peripheral lock = new Peripheral();
        lock.setType("Lock");
        lock.setId(1);
        mPeripherals.add(lock);

        Peripheral light = new Peripheral();
        light.setType("Light");
        light.setId(2);
        mPeripherals.add(light);

    }

    public List<Peripheral> getPeripherals() {
        return mPeripherals;
    }

    public Peripheral getPeripheral(int id) {
//        Log.i(TAG, "" + getPeripherals().get(0).getuuId());

        for (Peripheral p : mPeripherals) {

            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
