package com.rugged.application.hestia.backend.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import hestia.backend.NetworkHandler;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.Activator;
import hestia.backend.models.ActivatorState;
import hestia.backend.models.Device;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ActivatorTest {
    private final String DEFAULT_ID = "0";
    private final String DEFAULT_NAME = "TEST_ACTIVATOR";
    private final Integer DEFAULT_BOOL_RANK = 0;
    private final Boolean DEFAULT_BOOL_VALUE = false;
    private ActivatorState<Boolean> boolActivatorState;
    private Activator boolActivator;

    private final String DEFAULT_BOOL_TYPE = "bool";
    private final Integer DEFAULT_FLOAT_RANK = 1;
    private final Float DEFAULT_FLOAT_VALUE = 0.5f;
    private final String DEFAULT_FLOAT_TYPE = "float";
    private ActivatorState<Float> floatActivatorState;
    private Activator floatActivator;

    private Device device;
    private NetworkHandler handler;
    private Activator activatorWithHandlerAndDevice;

    @Before
    public void setUp(){
        floatActivatorState = new ActivatorState<>(DEFAULT_FLOAT_VALUE, DEFAULT_FLOAT_TYPE);
        floatActivator = new Activator(DEFAULT_ID, DEFAULT_FLOAT_RANK, floatActivatorState, DEFAULT_NAME);
        assertNotNull(floatActivator);

        boolActivatorState = new ActivatorState<>(DEFAULT_BOOL_VALUE, DEFAULT_BOOL_TYPE);
        boolActivator = new Activator(DEFAULT_ID, DEFAULT_BOOL_RANK, boolActivatorState, DEFAULT_NAME);
        assertNotNull(boolActivator);

        device = new Device("test", "test", "test", null, null);
        handler = new NetworkHandler("1.1.1.1", 1000);
        activatorWithHandlerAndDevice = new Activator(DEFAULT_ID, DEFAULT_BOOL_RANK,
                boolActivatorState, DEFAULT_NAME, device, handler);
        assertNotNull(activatorWithHandlerAndDevice);
    }

    @Test
    public void packageNameTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
    }

    @Test
    public void setAndGetIdTest() {
        assertEquals(DEFAULT_ID, boolActivator.getId());
        assertEquals(DEFAULT_ID, floatActivator.getId());

        String newId = "testId";
        boolActivator.setId(newId);
        floatActivator.setId(newId);

        assertEquals(newId, boolActivator.getId());
        assertEquals(newId, floatActivator.getId());
        assertNotEquals(DEFAULT_ID, boolActivator.getId());
        assertNotEquals(DEFAULT_ID, floatActivator.getId());
    }

    @Test
    public void setAndGetDeviceTest() {
        // test activator with device
        assertEquals(device, activatorWithHandlerAndDevice.getDevice());
        Device newDevice = new Device(null, null, null, null, null);
        assertNotNull(newDevice);
        assertNotSame(device, newDevice);
        activatorWithHandlerAndDevice.setDevice(newDevice);
        assertEquals(newDevice, activatorWithHandlerAndDevice.getDevice());
        assertNotNull(activatorWithHandlerAndDevice.getDevice());

        // test activator without device
        assertNull(boolActivator.getDevice());
        boolActivator.setDevice(newDevice);
        assertNotNull(boolActivator.getDevice());
        assertEquals(newDevice, boolActivator.getDevice());
    }

    @Test
    public void setAndGetHandlerTest() {
        // test activator with handler
        assertEquals(handler, activatorWithHandlerAndDevice.getHandler());
        NetworkHandler newHandler = new NetworkHandler(null, null);
        assertNotNull(newHandler);
        assertNotSame(handler, newHandler);
        activatorWithHandlerAndDevice.setHandler(newHandler);
        assertEquals(newHandler, activatorWithHandlerAndDevice.getHandler());
        assertNotNull(activatorWithHandlerAndDevice.getHandler());

        // test activator without handler
        assertNull(boolActivator.getHandler());
        boolActivator.setHandler(newHandler);
        assertNotNull(boolActivator.getHandler());
        assertEquals(newHandler, boolActivator.getHandler());
    }

    @Test
    public void setAndGetStateTestSuccess() throws IOException {
        assertEquals(boolActivatorState, activatorWithHandlerAndDevice.getState());

        // mock Network Handler so that the POST method will return a JsonObject similar to
        // the one returned when the request is successful
        NetworkHandler mockHandlerSuccess = mock(NetworkHandler.class);
        when(mockHandlerSuccess.POST(any(JsonObject.class), any(String.class))).thenReturn(new JsonObject());
        activatorWithHandlerAndDevice.setHandler(mockHandlerSuccess);

        // Attempt to change the state
        ActivatorState<Float> newFloatActivatorState = new ActivatorState<>(0.1f, "float");
        assertNotNull(newFloatActivatorState);
        assertNotSame(boolActivatorState, newFloatActivatorState);
        try {
            activatorWithHandlerAndDevice.setState(newFloatActivatorState);
        } catch (Exception exception) {
            fail("Test threw exception: " + exception.toString());
        }
        assertEquals(newFloatActivatorState, activatorWithHandlerAndDevice.getState());

        // Override POST method, now returning null.
        // Will use the boolActivatorState object as input
        when(mockHandlerSuccess.POST(any(JsonObject.class), any(String.class))).thenReturn(null);
        try {
            activatorWithHandlerAndDevice.setState(boolActivatorState);
        } catch (Exception exception){
            Assert.fail("Test threw exception: " + exception.toString());
        }
        assertEquals(boolActivatorState, activatorWithHandlerAndDevice.getState());
    }

    @Test(expected = ComFaultException.class)
    public void setAndGetStateTestComFaultException() throws IOException, ComFaultException {
        assertEquals(boolActivatorState, activatorWithHandlerAndDevice.getState());

        // Mock Network Handler so that the POST method will return a JsonObject similar to
        // the one returned when the request failed and a ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        JsonObject object = new JsonObject(); // Mock a JsonObject containing the error
        object.addProperty("error", "error_field");
        object.addProperty("message", "message_field");
        when(mockHandlerFail.POST(any(JsonObject.class), any(String.class))).thenReturn(object);
        activatorWithHandlerAndDevice.setHandler(mockHandlerFail);

        // Attempt to change the state
        ActivatorState<Float> newFloatActivatorState = new ActivatorState<>(0.1f, "float");
        assertNotNull(newFloatActivatorState);
        assertNotSame(boolActivatorState, newFloatActivatorState);
        activatorWithHandlerAndDevice.setState(newFloatActivatorState);
    }

    @Test(expected = IOException.class)
    public void setAndGetStateTestIOException() throws IOException, ComFaultException {
        assertEquals(boolActivatorState, activatorWithHandlerAndDevice.getState());

        // Mck Network Handler so that the POST method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.POST(any(JsonObject.class), any(String.class))).thenThrow(IOException.class);
        activatorWithHandlerAndDevice.setHandler(mockHandlerFail);

        // Attempt to change the state
        ActivatorState<Float> newFloatActivatorState = new ActivatorState<>(0.1f, "float");
        assertNotNull(newFloatActivatorState);
        assertNotSame(boolActivatorState, newFloatActivatorState);
        activatorWithHandlerAndDevice.setState(newFloatActivatorState);
    }

    @Test
    public void setAndGetNameTest() {
        assertEquals(DEFAULT_NAME, boolActivator.getName());
        assertEquals(DEFAULT_NAME, floatActivator.getName());

        String newName = "testName";
        boolActivator.setName(newName);
        floatActivator.setName(newName);

        assertEquals(newName, boolActivator.getName());
        assertEquals(newName, floatActivator.getName());
        assertNotEquals(DEFAULT_NAME, boolActivator.getName());
        assertNotEquals(DEFAULT_NAME, floatActivator.getName());
    }

    @Test
    public void setAndGetRankTest() {
        assertEquals(DEFAULT_BOOL_RANK, boolActivator.getRank());
        assertEquals(DEFAULT_FLOAT_RANK, floatActivator.getRank());

        Integer newRank = 10;
        boolActivator.setRank(newRank);
        floatActivator.setRank(newRank);

        assertEquals(newRank, boolActivator.getRank());
        assertEquals(newRank, floatActivator.getRank());
        assertNotEquals(DEFAULT_BOOL_RANK, boolActivator.getRank());
        assertNotEquals(DEFAULT_FLOAT_RANK, floatActivator.getRank());
    }

    @Test
    public void activatorEqualsAndHashTest(){
        // Testing hashCodes
        assertNotEquals(boolActivator.hashCode(), floatActivator.hashCode());
        assertEquals(boolActivator.hashCode(), boolActivator.hashCode());

        // Testing equals method
        assertTrue(boolActivator.equals(boolActivator));
        assertFalse(boolActivator.equals(floatActivator));
    }

    @Test
    public void toStringTest() {
        String boolToString = DEFAULT_NAME + " " + boolActivatorState.toString();
        String floatToString = DEFAULT_NAME + " " + floatActivatorState.toString();

        assertEquals(boolToString, boolActivator.toString());
        assertEquals(floatToString, floatActivator.toString());
    }
}
