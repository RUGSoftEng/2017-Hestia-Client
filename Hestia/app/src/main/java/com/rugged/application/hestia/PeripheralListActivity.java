package com.rugged.application.hestia;

import android.support.v4.app.Fragment;
import android.util.Log;

public class PeripheralListActivity extends SingleFragmentActivity{
    private final static String TAG = "PeripheralListAcitivity";
    @Override
    protected Fragment createFragment() {
        Log.i(TAG, "Stopped here");
        return new PeripheralListFragment();
    }
}
