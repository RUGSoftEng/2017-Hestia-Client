package com.rugged.application.hestia;

import android.content.Context;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ActivatorAndActivatorStateTest {
    private static final int DEFAULT_ID = 0;
    private static final String DEFAULT_NAME = "TEST_ACTIVATOR";
    private static ActivatorState<Float> floatActivatorState;
    private static ActivatorState<Boolean> boolActivatorState;

    private static Activator testFloatActivator;
    private static Activator testBoolActivator;

    @Before
    public void setUp(){
        floatActivatorState = new ActivatorState<Float>(Float.valueOf("122"),"UNSIGNED_BYTE");
        testFloatActivator = new Activator("1",1,floatActivatorState,"TEST_SLIDER_255");

        boolActivatorState = new ActivatorState<Boolean>(true,"TOGGLE");
        testBoolActivator = new Activator("1",1,boolActivatorState,"TEST_SWITCH");
    }

    @Test
    public void activatorStateTest(){
        int returnedFloatState = (int) floatActivatorState.getRawState().floatValue();
        assertEquals(returnedFloatState,122);
        assertEquals("UNSIGNED_BYTE",floatActivatorState.getType());

        boolean returnedBoolState = (boolean) boolActivatorState.getRawState();
        assertEquals(true,returnedBoolState);
        assertEquals("TOGGLE",boolActivatorState.getType());
    }

    @Test
    public void activatorConstructorTest(){
        assertEquals(floatActivatorState,testFloatActivator.getState());
        assertEquals(boolActivatorState,testBoolActivator.getState());
    }

}
