package com.rugged.application.hestia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import hestia.backend.ClientInteractionController;
import hestia.backend.Device;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ClientInteractionControllerTest {
    private String TAG = "ClientInteractionTest";

    @Test
    public void getDevices() throws Exception {
        ClientInteractionController cic = new ClientInteractionController("127.0.0.1:5000/");
        StringBuilder sb = new StringBuilder();
        for(Device d : cic.getDevices()){
            sb.append(d.toString());
        }
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
        Log.i(TAG, sb.toString());
    }
}