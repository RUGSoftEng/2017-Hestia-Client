package com.rugged.application.hestia;

import android.content.Context;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
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
    private static final int TEST_DEVICE_ID = 0;
    private static final int TEST_ACTIVATOR_ID = 0;
    private String TAG = "ClientInteractionTest";
    private static BackendInteractor backendInteractor;

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

    @Before
    public void addTestDevice(){
        ActivatorState<Boolean> testState = new ActivatorState<Boolean>(false,"TOGGLE");
        Activator testButton = new Activator(TEST_ACTIVATOR_ID,testState,"testButton");
        ArrayList<Activator> arr = new ArrayList<>();
        arr.add(testButton);
        Device testDevice = new Device(TEST_DEVICE_ID,"testDevice", "testing",arr);
        backendInteractor.addDevice(testDevice);
    }

    @After
    public void removeTestDevice(){
        backendInteractor.deleteTestDevice(TEST_DEVICE_ID);
    }


    @Test
    public void fail(){
        assertEquals(1,2);
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

    @Test
    public void testSetActivatorState(){
        ArrayList<Device> testDeviceList = backendInteractor.getDevices();
        Device testDevice = testDeviceList.get(TEST_DEVICE_ID);
        ActivatorState state = testDevice.getActivator(TEST_ACTIVATOR_ID).getState();
        boolean testState = (boolean)state.getRawState();
        assertEquals(testState,false);
        ActivatorState newState = new ActivatorState(true,"TOGGLE");
        backendInteractor.setActivatorState(TEST_DEVICE_ID,TEST_ACTIVATOR_ID,newState);
        testDeviceList = backendInteractor.getDevices();
        testDevice = testDeviceList.get(TEST_DEVICE_ID);
        assertEquals(testDevice.getActivator(TEST_ACTIVATOR_ID).getState().getRawState(),true);
    }



}