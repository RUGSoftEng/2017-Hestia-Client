package com.rugged.application.hestia.backendTests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rugged.application.hestia.backendTests.DummyObjects.DummyNetworkHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import hestia.backend.NetworkHandler;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.models.Activator;
import hestia.backend.models.Device;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class ServerCollectionsInteractorTest {
    private ServerCollectionsInteractor serverCollectionsInteractor;
    private Device dummyDevice;
    private String DUMMY_IP;
    private Integer DUMMY_PORT;

    @Before
    public void setUp() {
        serverCollectionsInteractor = new ServerCollectionsInteractor(new DummyNetworkHandler("127.0.0.1", 80));
        dummyDevice = new Device("dummyId","dummyName","dummyType",new ArrayList<Activator>(), new DummyNetworkHandler("127.0.0.1", 80));
        DUMMY_IP = "0.0.0.0";
        DUMMY_PORT = 1000;
    }

    @Test
    public void packageNameTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
    }

    @Test
    public void getDevicesTest(){
    }

    @Test
    public void addDeviceTest() {
    }

    @Test
    public void removeDeviceTest(){
    }

    @Test
    public void getCollectionsTest(){
    }

    @Test
    public void getPluginsTest(){
    }

    @Test
    public void getRequiredInfoTest(){
    }

    @Test
    public void setAndGetIpTest(){
    }

    @Test
    public void setAndGetPortTest() {
    }


}
