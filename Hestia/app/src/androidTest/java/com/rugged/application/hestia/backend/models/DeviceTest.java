package com.rugged.application.hestia.backend.models;

import android.support.test.runner.AndroidJUnit4;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.ArrayList;
import hestia.backend.NetworkHandler;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.Activator;
import hestia.backend.models.ActivatorState;
import hestia.backend.models.Device;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DeviceTest {
    private Device deviceTest;
    private ArrayList<Activator> activators;
    private NetworkHandler handler;
    private final String DEFAULT_DEVICE_ID = "12";
    private final String DEFAULT_DEVICE_NAME = "deviceTest";
    private final String DEFAULT_DEVICE_TYPE = "deviceType";

    @Before
    public void createDevice(){
        ActivatorState<Boolean> testState = new ActivatorState<>(false,"bool");
        Activator testButton = new Activator("0",0,testState,"testButton");
        activators = new ArrayList<>();
        activators.add(testButton);

        String defaultIp = "127.0.0.1";
        Integer defaultPort = 1000;
        handler = new NetworkHandler(defaultIp, defaultPort);

        deviceTest = new Device(DEFAULT_DEVICE_ID,DEFAULT_DEVICE_NAME,DEFAULT_DEVICE_TYPE,activators, handler);
    }

    @Test
    public void setAndGetIdTest() {
        assertEquals(DEFAULT_DEVICE_ID,deviceTest.getId());
        deviceTest.setId("2");
        assertEquals("2",deviceTest.getId());
    }

    @Test
    public void setAndGetTypeTest() {
        assertEquals(DEFAULT_DEVICE_TYPE,deviceTest.getType());
        deviceTest.setType("T_DEV");
        assertEquals("T_DEV", deviceTest.getType());
    }

    @Test
    public void setAndGetActivatorsTest() {
        assertFalse(deviceTest.getActivators().isEmpty());
        deviceTest.getActivators().clear();
        assertTrue(deviceTest.getActivators().isEmpty());

        ActivatorState<Float> testSliderState = new ActivatorState<>((float) 0.3,"float");
        Activator testSlider = new Activator("0",0,testSliderState,"SLIDER");
        ArrayList<Activator> newActivatorsList = new ArrayList<>();
        newActivatorsList.add(testSlider);
        deviceTest.setActivators(newActivatorsList);
        assertFalse(deviceTest.getActivators().isEmpty());
        assertTrue(deviceTest.getActivators().contains(testSlider));
    }

    @Test
    public void setAndGetHandlerTest() {
        assertEquals(handler,deviceTest.getHandler());
        String newIp = "1.1.1.1";
        Integer newPort = 2000;
        NetworkHandler newHandler = new NetworkHandler(newIp, newPort);
        deviceTest.setHandler(newHandler);
        assertEquals(newHandler, deviceTest.getHandler());
    }

    @Test
    public void setAndGetNameSuccess() throws IOException, ComFaultException {
        assertEquals(DEFAULT_DEVICE_NAME,deviceTest.getName());

        // mock Network Handler so that the PUT method will return a JsonObject similar to
        // the one returned when the request is successful
        NetworkHandler mockHandlerSuccess = mock(NetworkHandler.class);
        JsonObject object = new JsonObject();
        object.addProperty("name", "name_field");
        when(mockHandlerSuccess.PUT(any(JsonObject.class), any(String.class))).thenReturn(object);
        deviceTest.setHandler(mockHandlerSuccess);

        // attempt to change the name
        String newNameSuccess = "newNameSuccess";
        deviceTest.setName(newNameSuccess);
        assertEquals(newNameSuccess, deviceTest.getName());
    }

    @Test(expected = ComFaultException.class)
    public void setAndGetNameFailComFaultException() throws IOException, ComFaultException {
        assertEquals(DEFAULT_DEVICE_NAME,deviceTest.getName());

        // mock Network Handler so that the PUT method will return a JsonObject similar to
        // the one returned when the request failed and a ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        JsonObject object = new JsonObject(); // Mock a JsonObject containing the error
        object.addProperty("error", "error_field");
        object.addProperty("message", "message_field");
        when(mockHandlerFail.PUT(any(JsonObject.class), any(String.class))).thenReturn(object);
        deviceTest.setHandler(mockHandlerFail);

        // attempt to change the name
        String newNameFail = "newNameFail";
        deviceTest.setName(newNameFail);
        assertEquals(DEFAULT_DEVICE_NAME,deviceTest.getName());
    }

    @Test(expected = IOException.class)
    public void setAndGetNameFailIOException() throws IOException, ComFaultException {
        assertEquals(DEFAULT_DEVICE_NAME,deviceTest.getName());

        // mock Network Handler so that the PUT method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.PUT(any(JsonObject.class), any(String.class))).thenThrow(IOException.class);
        deviceTest.setHandler(mockHandlerFail);

        // attempt to change the name
        String newNameFail = "newNameFail";
        deviceTest.setName(newNameFail);
        assertEquals(DEFAULT_DEVICE_NAME,deviceTest.getName());
    }


    @Test
    public void toStringTest() {
        String TO_STRING = DEFAULT_DEVICE_NAME +" "+ DEFAULT_DEVICE_ID + " " + activators.toString() + "\n";
        assertEquals(TO_STRING, deviceTest.toString());
    }

    @Test
    public void equalsTest() {
        // create a new Device object with the same data as the deviceTest object
        Device dummyDevice = new Device(DEFAULT_DEVICE_ID, DEFAULT_DEVICE_NAME, DEFAULT_DEVICE_TYPE,activators, handler);
        assertTrue(dummyDevice.equals(deviceTest));
        assertEquals(dummyDevice, deviceTest);
    }
}