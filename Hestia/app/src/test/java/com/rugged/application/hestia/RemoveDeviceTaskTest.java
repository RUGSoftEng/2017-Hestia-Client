package com.rugged.application.hestia;

import android.os.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import hestia.backend.RemoveDeviceTask;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class RemoveDeviceTaskTest {
    final RemoveDeviceTask mockRemoveDevice = mock(RemoveDeviceTask.class);
    final Integer MOCK_RESULT = 204;

    @Test
    public void executeTest() throws Exception {
        when(mockRemoveDevice.execute()).then(new Answer<AsyncTask>() {
            @Override
            public AsyncTask answer(InvocationOnMock invocation) throws Throwable {
                when(mockRemoveDevice.get()).thenReturn(MOCK_RESULT);
                return mockRemoveDevice;
            }
        });
        mockRemoveDevice.execute();
        verify(mockRemoveDevice).execute();
        int result = mockRemoveDevice.get();
        verify(mockRemoveDevice).get();
        assertTrue(result==MOCK_RESULT);
    }
/*
    @Test
    public void doInBackgroundTest() {
        when(mockRemoveDevice.doInBackground()).thenReturn(anyInt());
        mockRemoveDevice.doInBackground();
        verify(mockRemoveDevice).doInBackground();
    }


    @Test
    public void onPostExecuteTest() {
        doNothing().when(mockRemoveDevice).onPostExecute(anyInt());
        mockRemoveDevice.onPostExecute(anyInt());
        verify(mockRemoveDevice).onPostExecute(anyInt());
    }*/

}