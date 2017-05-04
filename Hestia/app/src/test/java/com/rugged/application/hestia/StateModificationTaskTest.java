package com.rugged.application.hestia;

import android.os.AsyncTask;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import hestia.backend.StateModificationTask;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StateModificationTaskTest {
    final StateModificationTask mockStateModification = mock(StateModificationTask.class);
    final Integer MOCK_RESULT = 201;

    @Test
    public void executeTest() throws Exception {
        when(mockStateModification.execute()).then(new Answer<AsyncTask>() {
            @Override
            public AsyncTask answer(InvocationOnMock invocation) throws Throwable {
                when(mockStateModification.get()).thenReturn(MOCK_RESULT);
                return mockStateModification;
            }
        });
        mockStateModification.execute();
        verify(mockStateModification).execute();
        Integer result = mockStateModification.get();
        verify(mockStateModification).get();
        assertTrue(result==MOCK_RESULT);
    }

    @Test
    public void doInBackgroundTest() {
        when(mockStateModification.doInBackground()).thenReturn(MOCK_RESULT);
        mockStateModification.doInBackground();
        verify(mockStateModification).doInBackground();
    }
}
