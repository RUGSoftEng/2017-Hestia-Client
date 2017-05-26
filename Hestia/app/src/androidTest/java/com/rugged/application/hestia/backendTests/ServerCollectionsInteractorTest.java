package com.rugged.application.hestia.backendTests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
        dummyDevice = new Device("dummyId","dummyName","dummyType",new ArrayList<Activator>());
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

    private class DummyNetworkHandler extends NetworkHandler{

        /* TODO implement this class to mcok the behaviour of the network handler based on the endpoint given. */

        public DummyNetworkHandler(String ip, int port) {
            super(ip, port);
        }

        public JsonElement GET(String endpoint) throws IOException {
            JsonElement payload = new JsonObject();
            return payload;
        }

        public JsonElement POST(JsonObject object, String endpoint) throws IOException {
            JsonElement payload = new JsonObject();
            return payload;
        }

        public JsonElement DELETE(String endpoint) throws IOException {
            JsonElement payload = new JsonObject();
            return payload;
        }

        public JsonElement PUT(JsonObject object, String endpoint) throws IOException {
            JsonElement payload = new JsonObject();
            return payload;
        }
    }

}
