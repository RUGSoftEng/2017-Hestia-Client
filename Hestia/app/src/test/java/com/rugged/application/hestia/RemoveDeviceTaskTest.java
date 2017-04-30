package com.rugged.application.hestia;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import hestia.backend.RemoveDeviceTask;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RemoveDeviceTaskTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        //  create mock
        RemoveDeviceTask test = mock(RemoveDeviceTask.class);
        Integer response = test.execute().get();
        assertTrue(response!=null && response == 204);
    }

}