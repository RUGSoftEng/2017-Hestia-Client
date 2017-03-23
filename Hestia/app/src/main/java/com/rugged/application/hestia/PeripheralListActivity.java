package com.rugged.application.hestia;

import android.support.v4.app.Fragment;

public class PeripheralListActivity extends SingleFragmentActivity{
    private final static String TAG = "PeripheralListActivity";
    @Override
    protected Fragment createFragment() {
        return new PeripheralListFragment();
    }
}
