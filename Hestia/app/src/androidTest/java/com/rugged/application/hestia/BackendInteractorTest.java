package com.rugged.application.hestia;

import android.content.Context;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BackendInteractorTest {
    private String TAG = "ClientInteractionTest";
    private static BackendInteractor backendInteractor;

    //Utility functions for the test cases down below
    public void addTestDevice(){
        ActivatorState<Boolean> testState = new ActivatorState<Boolean>(false,"TOGGLE");
        Activator testButton = new Activator(0,testState,"testButton");
        ArrayList<Activator> arr = new ArrayList<>();
        arr.add(testButton);
        Device testDevice = new Device(0,"testDevice", "testing",arr);
        backendInteractor.addDevice(testDevice);
    }

    @BeforeClass
    public static void runBeforeTests(){
        backendInteractor = BackendInteractor.getInstance();
        ActivatorState<Boolean> testState = new ActivatorState<Boolean>(false,"TOGGLE");
        Activator testButton = new Activator(0,testState,"testButton");
        ArrayList<Activator> arr = new ArrayList<>();
        arr.add(testButton);
        Device testDevice = new Device(0,"testDevice", "testing",arr);
        backendInteractor.addDevice(testDevice);
    }

    @Test
    public void testPackageName(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
    }

    @Test
    public void testGetDevices(){
        StringBuilder sb = new StringBuilder();
        for(Device d : backendInteractor.getDevices()){
            sb.append(d.toString());
        }
        Log.i(TAG, sb.toString());
    }

    /*
     * Test of the singleton reference, two different references should refer to the same object.
     */
    @Test
    public void testSingleton(){
        BackendInteractor copyOfInteractor = BackendInteractor.getInstance();
        assertEquals(backendInteractor,copyOfInteractor);
    }

}