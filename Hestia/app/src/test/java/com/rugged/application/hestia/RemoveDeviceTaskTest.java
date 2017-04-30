package com.rugged.application.hestia;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.concurrent.ExecutionException;
import hestia.backend.RemoveDeviceTask;
import static org.mockito.Mockito.*;

public class RemoveDeviceTaskTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    public void testWithMock() throws InterruptedException, ExecutionException{
        RemoveDeviceTask mockRemoveDevice = mock(RemoveDeviceTask.class);
        when(mockRemoveDevice.doInBackground()).thenReturn(204);
        mockRemoveDevice.doInBackground();
        verify(mockRemoveDevice).doInBackground();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}