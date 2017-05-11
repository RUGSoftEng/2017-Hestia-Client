package com.rugged.application.hestia;

import android.os.AsyncTask;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import hestia.backend.PostDeviceTask;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostDeviceTaskTest {
    final PostDeviceTask mockPostDevice = mock(PostDeviceTask.class);
    final Integer MOCK_RESULT = 201;

    @Test
    public void executeTest() throws Exception {
        when(mockPostDevice.execute()).then(new Answer<AsyncTask>() {
            @Override
            public AsyncTask answer(InvocationOnMock invocation) throws Throwable {
                when(mockPostDevice.get()).thenReturn(MOCK_RESULT);
                return mockPostDevice;
            }
        });
        mockPostDevice.execute();
        verify(mockPostDevice).execute();
        Integer result = mockPostDevice.get();
        verify(mockPostDevice).get();
        assertTrue(result==MOCK_RESULT);
    }

    /*
    @Test
    public void doInBackgroundTest() {
        when(mockPostDevice.execute()).thenReturn(MOCK_RESULT);
        mockPostDevice.execute();
        verify(mockPostDevice).execute();
    }

    @Test
    public void onPostExecuteTest() {
        doNothing().when(mockPostDevice).onPostExecute(MOCK_RESULT);
        mockPostDevice.onPostExecute(MOCK_RESULT);
        verify(mockPostDevice).onPostExecute(MOCK_RESULT);
    }*/
}
