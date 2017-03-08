package com.rugged.application.hestia;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class PeripheralActivity extends SingleFragmentActivity{
    public static final String EXTRA_PERIPHERAl_ID =
            "com.rugged.application.hestia.peripheral_id";

    public static Intent newIntent(Context packageContext, int peripheralId) {
        Intent intent = new Intent(packageContext, PeripheralActivity.class);
        intent.putExtra(EXTRA_PERIPHERAl_ID, peripheralId);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        return new PeripheralFragment();
    }
}


