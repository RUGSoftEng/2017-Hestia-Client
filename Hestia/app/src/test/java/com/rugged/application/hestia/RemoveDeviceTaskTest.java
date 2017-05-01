package com.rugged.application.hestia;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import hestia.backend.RemoveDeviceTask;
import static org.mockito.Mockito.*;

public class RemoveDeviceTaskTest extends TestCase {

    @Test
    public void testWithMock() throws Exception {
        final RemoveDeviceTask mockRemoveDevice = mock(RemoveDeviceTask.class);
        when(mockRemoveDevice.doInBackground()).thenReturn(204);
        when(mockRemoveDevice.execute()).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                when(mockRemoveDevice.get()).thenReturn(204);
                verify(mockRemoveDevice).get();
                return true;
            }
        });
        mockRemoveDevice.doInBackground();
        verify(mockRemoveDevice).doInBackground();
    }
}