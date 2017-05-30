package com.rugged.application.hestia.UI;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import hestia.TempDiscoClass;

@RunWith(AndroidJUnit4.class)
public class DiscoTest {

    @Test
    public void DiscoveryTest(){
        new TempDiscoClass().execute();
    }

}
