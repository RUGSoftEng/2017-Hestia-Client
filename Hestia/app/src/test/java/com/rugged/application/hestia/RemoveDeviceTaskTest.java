package com.rugged.application.hestia;

import android.test.UiThreadTest;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import hestia.backend.Device;
import hestia.backend.RemoveDeviceTask;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RemoveDeviceTaskTest extends TestCase {

    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
    }

    public void testAsyncTask() throws InterruptedException
    {
        new RemoveDeviceTask(new Device(1, null, null, null));
        signal.await(5, TimeUnit.SECONDS);
        assertTrue( "assert something meaningful here", true );
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}