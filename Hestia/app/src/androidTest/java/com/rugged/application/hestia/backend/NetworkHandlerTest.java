package com.rugged.application.hestia.backend;

import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import hestia.backend.NetworkHandler;
import static junit.framework.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NetworkHandlerTest {
    private NetworkHandler dummyHandler;
    private final String dummyIP = "0.0.0.0";
    private final Integer dummyPort = 1000;

    @Before
    public void setUp() {
        assertNull(dummyHandler);
        dummyHandler = new NetworkHandler(dummyIP, dummyPort);
        assertNotNull(dummyHandler);
    }

    @Test
    public void packageTest() {}

    @Test
    public void setAndGetIpTest() {
        assertEquals(dummyIP, dummyHandler.getIp());
        String newIP ="1.0.0.0";
        dummyHandler.setIp(newIP);
        assertEquals(newIP, dummyHandler.getIp());
    }

    @Test
    public void setAndGetPortTest() {
        assertEquals(dummyPort, dummyHandler.getPort());
        Integer newPort = 2000;
        dummyHandler.setPort(newPort);
        assertEquals(newPort, dummyHandler.getPort());
    }

    @Test
    public void isSuccessfulRequestTest() {
        Integer responseCode1 = 200;
        Integer responseCode2 = 404;
        Integer responseCode3 = null;
        assertTrue(dummyHandler.isSuccessfulRequest(responseCode1));
        assertFalse(dummyHandler.isSuccessfulRequest(responseCode2));
        assertFalse(dummyHandler.isSuccessfulRequest(responseCode3));
    }

    @Test
    public void getDefaultPathTest() {
        String path1 = "http://" + dummyIP + ":" + dummyPort + "/";
        assertNotNull(dummyHandler.getDefaultPath());
        assertEquals(path1, dummyHandler.getDefaultPath());

        String IP = "1.1.1.1";
        Integer port = 2000;
        String path2 = "http://" + IP + ":" + port + "/";
        dummyHandler.setIp(IP);
        dummyHandler.setPort(port);
        assertNotNull(dummyHandler.getDefaultPath());
        assertEquals(path2, dummyHandler.getDefaultPath());
    }

    @Test
    public void equalsTest() {}


    @After
    public void tearDown() {
        assertNotNull(dummyHandler);
        dummyHandler = null;
        assertNull(dummyHandler);
    }

}
