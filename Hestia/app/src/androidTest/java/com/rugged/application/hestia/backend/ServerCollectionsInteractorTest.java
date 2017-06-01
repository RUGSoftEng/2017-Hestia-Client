package com.rugged.application.hestia.backend;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import hestia.backend.NetworkHandler;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.Device;
import hestia.backend.models.RequiredInfo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ServerCollectionsInteractorTest {
    private ServerCollectionsInteractor dummyServerCollectionsInteractor;
    private NetworkHandler DEFAULT_HANDLER;

    @Before
    public void setUp() {
        String defaultIp= "0.0.0.0";
        Integer defaultPort = 1000;
        DEFAULT_HANDLER = new NetworkHandler(defaultIp, defaultPort);
        dummyServerCollectionsInteractor = new ServerCollectionsInteractor(DEFAULT_HANDLER);
    }

    @Test
    public void packageNameTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
    }

    @Test
    public void setAndGetHandlerTest() {
        assertEquals(DEFAULT_HANDLER, dummyServerCollectionsInteractor.getHandler());
        String newIp = "2.2.2.2";
        Integer newPort = 3000;
        NetworkHandler newHandler = new NetworkHandler(newIp, newPort);
        assertNotSame(DEFAULT_HANDLER, newHandler);
        dummyServerCollectionsInteractor.setHandler(newHandler);
        assertEquals(newHandler, dummyServerCollectionsInteractor.getHandler());
    }

    /**
     * getDevices tests
     */
    @Test
    public void getDevicesTestSuccess() throws IOException, ComFaultException {
        // Mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request was successful
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy JsonArray, containing 1 JsonObject holding information of a dummy Device.
        // It contains a dummy id, a dummy name, a dummy type, and an empty list of activators.
        JsonArray devicesJsonArray = new JsonArray();
        JsonObject deviceObject = new JsonObject();
        deviceObject.addProperty("deviceId", "dummyId");
        deviceObject.addProperty("name", "dummyName");
        deviceObject.addProperty("type", "dummyType");
        deviceObject.add("activators", new JsonArray());
        devicesJsonArray.add(deviceObject);
        when(mockHandlerFail.GET(any(String.class))).thenReturn(devicesJsonArray);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of devices
        ArrayList<Device> devices = dummyServerCollectionsInteractor.getDevices();
        assertNotNull(devices);
        assertEquals(devices.get(0).getId(), deviceObject.get("deviceId").getAsString());
        assertEquals(devices.get(0).getName(), deviceObject.get("name").getAsString());
        assertEquals(devices.get(0).getType(), deviceObject.get("type").getAsString());
        assertNotNull(devices.get(0).getActivators());
    }

    @Test(expected = ComFaultException.class)
    public void getDevicesTestComFaultException() throws IOException, ComFaultException {
        // Mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy error JsonObject.
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", "error_field");
        errorObject.addProperty("message", "message_field");
        when(mockHandlerFail.GET(any(String.class))).thenReturn(errorObject);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of devices
        ArrayList<Device> devices = dummyServerCollectionsInteractor.getDevices();
    }

    @Test(expected = IOException.class)
    public void getDevicesTestIOException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.GET(any(String.class))).thenThrow(IOException.class);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of devices
        ArrayList<Device> devices = dummyServerCollectionsInteractor.getDevices();
    }

    /**
     * addDevice tests
     */
    @Test
    public void addDeviceTestSuccessful() throws IOException {
        // Mock Network Handler so that the POST method will return a JsonObject similar to
        // the one returned when the request was successful
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy RequiredInfo as a JsonObject, which will be used to as a return value.
        JsonObject requiredInfo = new JsonObject();
        requiredInfo.addProperty("collection", "dummyCollection");
        requiredInfo.addProperty("plugin_name", "dummyPluginName");
        requiredInfo.add("required_info", new JsonObject());
        when(mockHandlerFail.POST(any(JsonObject.class), any(String.class))).thenReturn(requiredInfo);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to add a device, based on a dummy RequiredInfo object
        String dummyCollection = "dummy_collection";
        String dummyPlugin = "dummy_plugin";
        HashMap<String, String> dummyInfo = new HashMap<>();
        RequiredInfo dummyRequiredInfo = new RequiredInfo(dummyCollection, dummyPlugin, dummyInfo);
        try {
            dummyServerCollectionsInteractor.addDevice(dummyRequiredInfo);
        } catch (Exception exception){
            fail("Test threw exception: " + exception.toString());
        }
    }

    @Test(expected = ComFaultException.class)
    public void addDeviceTestComFaultException() throws IOException, ComFaultException {
        // mock Network Handler so that the POST method will return a JsonObject similar to
        // the one returned when the request failed and an ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy error JsonObject.
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", "error_field");
        errorObject.addProperty("message", "message_field");
        when(mockHandlerFail.POST(any(JsonObject.class), any(String.class))).thenReturn(errorObject);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to add a device, based on a dummy RequiredInfo object
        String dummyCollection = "dummy_collection";
        String dummyPlugin = "dummy_plugin";
        HashMap<String, String> dummyInfo = new HashMap<>();
        RequiredInfo dummyRequiredInfo = new RequiredInfo(dummyCollection, dummyPlugin, dummyInfo);
        dummyServerCollectionsInteractor.addDevice(dummyRequiredInfo);
    }

    @Test(expected = IOException.class)
    public void addDeviceTestIOException() throws IOException, ComFaultException {
        // mock Network Handler so that the POST method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.POST(any(JsonObject.class), any(String.class))).thenThrow(IOException.class);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to add a device, based on a dummy RequiredInfo object
        String dummyCollection = "dummy_collection";
        String dummyPlugin = "dummy_plugin";
        HashMap<String, String> dummyInfo = new HashMap<>();
        RequiredInfo dummyRequiredInfo = new RequiredInfo(dummyCollection, dummyPlugin, dummyInfo);
        dummyServerCollectionsInteractor.addDevice(dummyRequiredInfo);

    }

    /**
     * removeDevice tests
     */
    @Test
    public void removeDeviceTestSuccessful(){
    }

    @Test(expected = ComFaultException.class)
    public void removeDeviceTestComFaultException() throws IOException, ComFaultException {
        // mock Network Handler so that the DELETE method will return a JsonObject similar to
        // the one returned when the request failed and an ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy error JsonObject.
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", "error_field");
        errorObject.addProperty("message", "message_field");
        when(mockHandlerFail.DELETE(any(String.class))).thenReturn(errorObject);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to delete a dummy device. Note: only the ID must not be null.
        String dummyId = "dummy_id";
        Device dummyDevice = new Device(dummyId, null, null, null, null);
        dummyServerCollectionsInteractor.removeDevice(dummyDevice);
    }

    @Test(expected = IOException.class)
    public void removeDeviceTestIOException() throws IOException, ComFaultException {
        // mock Network Handler so that the DELETE method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.DELETE(any(String.class))).thenThrow(IOException.class);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to delete a dummy device. Note: only the ID must not be null.
        String dummyId = "dummy_id";
        Device dummyDevice = new Device(dummyId, null, null, null, null);
        dummyServerCollectionsInteractor.removeDevice(dummyDevice);
    }

    /**
     * getCollections tests
     */
    @Test
    public void getCollectionsTestSuccess() throws IOException, ComFaultException {

    }

    @Test(expected = ComFaultException.class)
    public void getCollectionsTestComFaultException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy error JsonObject.
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", "error_field");
        errorObject.addProperty("message", "message_field");
        when(mockHandlerFail.GET(any(String.class))).thenReturn(errorObject);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of collections
        ArrayList<String> collections = dummyServerCollectionsInteractor.getCollections();
    }

    @Test(expected = IOException.class)
    public void getCollectionsTestIOException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.GET(any(String.class))).thenThrow(IOException.class);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of collections
        ArrayList<String> collections = dummyServerCollectionsInteractor.getCollections();
    }

    /**
     * getPlugins tests
     */
    @Test
    public void getPluginsTestSuccess() {

    }

    @Test(expected = ComFaultException.class)
    public void getPluginsTestComFaultException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy error JsonObject.
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", "error_field");
        errorObject.addProperty("message", "message_field");
        when(mockHandlerFail.GET(any(String.class))).thenReturn(errorObject);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of plugins
        String dummyCollection = "dummy_collection";
        ArrayList<String> plugins = dummyServerCollectionsInteractor.getPlugins(dummyCollection);
    }

    @Test(expected = IOException.class)
    public void getPluginsTestIOException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.GET(any(String.class))).thenThrow(IOException.class);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the list of plugins
        String dummyCollection = "dummy_collection";
        ArrayList<String> plugins = dummyServerCollectionsInteractor.getPlugins(dummyCollection);
    }

    /**
     * getRequiredInfo tests
     */
    @Test
    public void getRequiredInfoTestSuccessful() {
    }

    @Test(expected = ComFaultException.class)
    public void getRequiredInfoTestComFaultException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an ComFaultException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);

        // Creates a dummy error JsonObject.
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", "error_field");
        errorObject.addProperty("message", "message_field");
        when(mockHandlerFail.GET(any(String.class))).thenReturn(errorObject);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the required info
        String dummyCollections = "dummy_collections";
        String dummyPlugins = "dummyPlugins";
        RequiredInfo requiredInfo = dummyServerCollectionsInteractor
                                        .getRequiredInfo(dummyCollections, dummyPlugins);
    }

    @Test(expected = IOException.class)
    public void getRequiredInfoTestIOException() throws IOException, ComFaultException {
        // mock Network Handler so that the GET method will return a JsonObject similar to
        // the one returned when the request failed and an IOException was thrown
        NetworkHandler mockHandlerFail = mock(NetworkHandler.class);
        when(mockHandlerFail.GET(any(String.class))).thenThrow(IOException.class);
        dummyServerCollectionsInteractor.setHandler(mockHandlerFail);

        // attempt to get the required info
        String dummyCollections = "dummy_collections";
        String dummyPlugins = "dummyPlugins";
        RequiredInfo requiredInfo = dummyServerCollectionsInteractor
                                        .getRequiredInfo(dummyCollections, dummyPlugins);
    }
}
