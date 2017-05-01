package com.rugged.application.hestia;

import android.os.AsyncTask;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.HashMap;
import hestia.backend.PluginInformationRetrieverTask;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginInformationRetrieverTaskTest {
    final PluginInformationRetrieverTask mockPluginInformationRetriever = mock(PluginInformationRetrieverTask.class);
    final HashMap<String, String> MOCK_RESULT = new HashMap<>();

    @Test
    public void executeTest() throws Exception {
        when(mockPluginInformationRetriever.execute()).then(new Answer<AsyncTask>() {
            @Override
            public AsyncTask answer(InvocationOnMock invocation) throws Throwable {
                when(mockPluginInformationRetriever.get()).thenReturn(MOCK_RESULT);
                return mockPluginInformationRetriever;
            }
        });
        mockPluginInformationRetriever.execute();
        verify(mockPluginInformationRetriever).execute();
        HashMap<String, String> result = mockPluginInformationRetriever.get();
        verify(mockPluginInformationRetriever).get();
        assertTrue(result==MOCK_RESULT);
    }

    @Test
    public void doInBackgroundTest() {
        when(mockPluginInformationRetriever.doInBackground()).thenReturn(MOCK_RESULT);
        mockPluginInformationRetriever.doInBackground();
        verify(mockPluginInformationRetriever).doInBackground();
    }

    @Test
    public void onPostExecuteTest() {
        doNothing().when(mockPluginInformationRetriever).onPostExecute(MOCK_RESULT);
        mockPluginInformationRetriever.onPostExecute(MOCK_RESULT);
        verify(mockPluginInformationRetriever).onPostExecute(MOCK_RESULT);
    }
}
