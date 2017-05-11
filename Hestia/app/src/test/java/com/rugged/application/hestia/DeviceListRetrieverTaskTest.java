package com.rugged.application.hestia;

import android.os.AsyncTask;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.ArrayList;
import hestia.backend.Device;
import hestia.backend.DeviceListRetrieverTask;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeviceListRetrieverTaskTest {
    final DeviceListRetrieverTask mockDeviceListRetriever = mock(DeviceListRetrieverTask.class);
    final ArrayList<Device> MOCK_RESULT = new ArrayList<>();

    @Test
    public void executeTest() throws Exception {
        when(mockDeviceListRetriever.execute()).then(new Answer<AsyncTask>() {
            @Override
            public AsyncTask answer(InvocationOnMock invocation) throws Throwable {
                when(mockDeviceListRetriever.get()).thenReturn(MOCK_RESULT);
                return mockDeviceListRetriever;
            }
        });
        mockDeviceListRetriever.execute();
        verify(mockDeviceListRetriever).execute();
        ArrayList<Device> result = mockDeviceListRetriever.get();
        verify(mockDeviceListRetriever).get();
        assertTrue(result==MOCK_RESULT);
    }

    /*
    @Test
    public void doInBackgroundTest() {
        when(mockDeviceListRetriever.doInBackground()).thenReturn(MOCK_RESULT);
        mockDeviceListRetriever.doInBackground();
        verify(mockDeviceListRetriever).doInBackground();
    }
*/

    @Test
    public void onPostExecuteTest() {
        doNothing().when(mockDeviceListRetriever).onPostExecute(MOCK_RESULT);
        mockDeviceListRetriever.onPostExecute(MOCK_RESULT);
        verify(mockDeviceListRetriever).onPostExecute(MOCK_RESULT);
    }
}
