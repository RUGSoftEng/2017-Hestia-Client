package com.rugged.application.hestia.backend;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
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
import static org.junit.Assert.assertNotSame;
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
    public void getDevicesTestSuccess(){

    }

    @Test(expected = ComFaultException.class)
    public void getDevicesTestComFaultException() {
        
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
    public void addDeviceTestSuccessful() {
    }

    @Test(expected = ComFaultException.class)
    public void addDeviceTestComFaultException() {

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
    public void removeDeviceTestComFaultException() {

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
    public void getCollectionsTestSuccess() {

    }

    @Test(expected = ComFaultException.class)
    public void getCollectionsTestComFaultException() {

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
    public void getPluginsTestComFaultException() {

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
    public void getRequiredInfoTestComFaultException() {

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
