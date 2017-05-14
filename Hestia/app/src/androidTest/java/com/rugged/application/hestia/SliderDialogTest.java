package com.rugged.application.hestia;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import hestia.UI.DeviceListActivity;
import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.BackendInteractor;
import hestia.backend.Device;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SliderDialogTest {
    private static final int TEST_DEVICE_ID = 0;
    private static final int TEST_ACTIVATOR_ID = 0;
    private static final int TEST_ACTIVATOR_ID1 = 1;
    private static BackendInteractor backendInteractor;

    @Rule
    public IntentsTestRule<DeviceListActivity> mIntentsRule =
            new IntentsTestRule<>(DeviceListActivity.class);

    @BeforeClass
    public static void runBeforeTests(){
        backendInteractor = BackendInteractor.getInstance();
        ActivatorState<Boolean> testState = new ActivatorState<Boolean>(false,"TOGGLE");
        Activator testButton = new Activator(0,testState,"testButton");
        ActivatorState<Float> testState1 = new ActivatorState<Float>(0.0f,"SLIDER");
        Activator testSlider = new Activator(1,testState1,"testSlider");
        ArrayList<Activator> arr = new ArrayList<>();
        arr.add(testButton);
        arr.add(testSlider);
        Device testDevice = new Device(0,"testDevice", "testing",arr);
        backendInteractor.addDevice(testDevice);
    }

    @Before
    public void addTestDevice(){
        ActivatorState<Boolean> testState = new ActivatorState<Boolean>(false,"TOGGLE");
        Activator testButton = new Activator(TEST_ACTIVATOR_ID,testState,"testButton");
        ArrayList<Activator> arr = new ArrayList<>();
        arr.add(testButton);
        ActivatorState<Float> testState1 = new ActivatorState<Float>(0.0f,"SLIDER");
        Activator testSlider = new Activator(TEST_ACTIVATOR_ID1,testState1,"testSlider");
        arr.add(testSlider);
        Device testDevice = new Device(TEST_DEVICE_ID,"testDevice", "testing",arr);
        backendInteractor.addDevice(testDevice);
    }

    @Test
    public void testSliders(){
        onView(withId(R.id.lblListHeader)).perform(click());

        onView(withId(R.id.imageview)).perform(click());

        onView(withText("Sliders")).perform(click());

        onView(withId(R.id.linearMain)).check(matches(isDisplayed()));
    }


}
