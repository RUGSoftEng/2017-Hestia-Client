package com.rugged.application.hestia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import hestia.backend.BackendInteractor;
import hestia.backend.Device;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BackendInteractorTest {
    private String TAG = "ClientInteractionTest";

    @Test
    public void getDevices() throws Exception {
        BackendInteractor cic = BackendInteractor.getInstance();
        StringBuilder sb = new StringBuilder();
        for(Device d : cic.getDevices()){
            sb.append(d.toString());
        }
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
        Log.i(TAG, sb.toString());
    }
}