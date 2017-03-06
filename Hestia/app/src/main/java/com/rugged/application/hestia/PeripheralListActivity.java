package com.rugged.application.hestia;


import android.support.v4.app.Fragment;

public class PeripheralListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new PeripheralListFragment();
    }
}
