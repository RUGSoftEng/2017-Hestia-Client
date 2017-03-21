/*
* This class takes care of the list of peripherals, it is a singleton class since the application
* should take care of only one PeripheralList
*/

package com.rugged.application.hestia;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

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
        //set standard peripherals, is hardcoded for now
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
        for (Peripheral p : mPeripherals) {

            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
