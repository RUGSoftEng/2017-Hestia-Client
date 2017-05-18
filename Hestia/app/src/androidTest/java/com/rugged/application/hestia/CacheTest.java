package com.rugged.application.hestia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import hestia.UI.DeviceListFragment;
import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.Cache;
import hestia.backend.Device;
import hestia.backend.DevicesChangeListener;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CacheTest {
    private String TAG = "ClientInteractionTest";
    private Cache cache;
    private Device dummyDevice;

    @Before
    public void addDeviceTest(){
        cache = Cache.getInstance();
        dummyDevice = new Device("dummyId","dummyName","dummyType",new ArrayList<Activator>());
        int sizeBefore = cache.getDevices().size();
        cache.getDevices().add(dummyDevice);
        int sizeAfter = cache.getDevices().size();
        assertNotEquals(sizeBefore, sizeAfter);
        assertTrue(cache.getDevices().contains(dummyDevice));
    }

    @Test
    public void packageNameTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rugged.application.hestia", appContext.getPackageName());
    }

    @Test
    public void getDevicesTest(){
        StringBuilder sb = new StringBuilder();
        for(Device d : cache.getDevices()){
            sb.append(d.toString());
        }
        Log.i(TAG, sb.toString());
    }

    /*
     * Test of the singleton reference, two different references should refer to the same object.
     */

    @Test
    public void singletonTest(){
        Cache copyOfCache = Cache.getInstance();
        assertEquals(cache,copyOfCache);
    }


    @Test
    public void setIpTest(){
        String testIp = "192.168.0.1";
        cache.setIp(testIp);
        assertEquals(testIp,cache.getIp());
    }

    @Test
    public void clearDevicesTest() {
        // empty list
        cache.getDevices().clear();
        assertTrue(cache.getDevices().isEmpty());
        assertEquals(cache.getDevices().size(), 0);

        // adding 3 devices
        cache.getDevices().add(new Device(null, null, null, null));
        cache.getDevices().add(new Device(null, null, null, null));
        cache.getDevices().add(new Device(null, null, null, null));
        assertEquals(cache.getDevices().size(), 3);

        // clearing the list again
        cache.getDevices().clear();
        assertTrue(cache.getDevices().isEmpty());
        assertEquals(cache.getDevices().size(), 0);
    }

    @Test
    public void deleteDeviceTest(){
        int lengthBefore = cache.getDevices().size();
        // Removing a device
        cache.getDevices().remove(dummyDevice);
        int lengthAfter = cache.getDevices().size();
        assertNotEquals(lengthBefore, lengthAfter);
        cache.getDevices().clear();
    }

    @Test
    public void setDevicesTest(){
        ArrayList<Device> newDevices = new ArrayList<>();

        // Adding the same dummy device three times
        newDevices.add(dummyDevice);
        newDevices.add(dummyDevice);
        newDevices.add(dummyDevice);

        // setting new list of devices
        cache.setDevices(newDevices);
        assertEquals(3,cache.getDevices().size());
    }

    @Test
    public void listenerTest(){
        DevicesChangeListener l = new DeviceListFragment();

        // Testing adding a listener
        cache.addDevicesChangeListener(l);
        assertEquals(l,cache.getListeners().get(0));

        // Testing removing a listener
        cache.removeDevicesChangeListener(l);
        assertEquals(0,cache.getListeners().size());
    }

    @After
    public void removeTestDevice(){
        cache.getDevices().remove(dummyDevice);
    }

}
