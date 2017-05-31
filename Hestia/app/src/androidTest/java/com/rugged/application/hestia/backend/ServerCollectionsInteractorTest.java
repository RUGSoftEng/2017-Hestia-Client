package com.rugged.application.hestia.backend;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import hestia.backend.NetworkHandler;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.Activator;
import hestia.backend.models.Device;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


@RunWith(AndroidJUnit4.class)
public class ServerCollectionsInteractorTest {
    private ServerCollectionsInteractor dummyServerCollectionsInteractor;
    private Device DEFAULT_DEVICE;
    private String DEFAULT_IP;
    private Integer DEFAULT_PORT;
    private NetworkHandler DEFAULT_HANDLER;

    @Before
    public void setUp() {
        DEFAULT_IP = "0.0.0.0";
        DEFAULT_PORT = 1000;
        DEFAULT_HANDLER = new NetworkHandler(DEFAULT_IP, DEFAULT_PORT);
        dummyServerCollectionsInteractor = new ServerCollectionsInteractor(DEFAULT_HANDLER);
        DEFAULT_DEVICE = new Device("dummyId","dummyName","dummyType",new ArrayList<Activator>(), DEFAULT_HANDLER);
    }

    @Test
    public void packageNameTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
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
    public void getDevicesTestIOException() {

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
    public void addDeviceTestIOException() {

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
    public void removeDeviceTestIOException() {

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
    public void getCollectionsTestIOException() {

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
    public void getPluginsTestIOException() {

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
    public void getRequiredInfoTestIOException() {

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

}
