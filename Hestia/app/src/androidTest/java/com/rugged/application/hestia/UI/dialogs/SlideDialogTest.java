//package com.rugged.application.hestia.UI.dialogs;
//
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.support.test.rule.ActivityTestRule;
//
//import com.rugged.application.hestia.R;
//import com.rugged.application.hestia.backend.dummyObjects.DummyNetworkHandler;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import hestia.UI.activities.home.HomeActivity;
//import hestia.backend.models.Activator;
//import hestia.backend.models.ActivatorState;
//import hestia.backend.models.Device;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
//public class SlideDialogTest {
//    private ArrayList<Activator> activators;
//    private Device testDevice;
//    private final Boolean DEFAULT_BOOL_RAW_STATE = false;
//    private final String DEFAULT_BOOL_TYPE = "bool";
//    private final Float DEFAULT_FLOAT_RAW_STATE = (float) 0.5f;
//    private final String DEFAULT_FLOAT_TYPE = "float";
//    private final String DEFAULT_DEVICE_ID = "12";
//    private final String DEFAULT_DEVICE_NAME = "deviceTest";
//    private final String DEFAULT_DEVICE_TYPE = "deviceType";
//    private ActivatorState<Float> floatState;
//    private ActivatorState<Boolean> boolState;
//
//    @Rule
//    public ActivityTestRule<HomeActivity> activityTestRule =
//            new ActivityTestRule<>(HomeActivity.class);
//
//    @Before
//    public void createDevice(){
//        boolState = new ActivatorState<>(DEFAULT_BOOL_RAW_STATE, DEFAULT_BOOL_TYPE);
//        Activator testButton = new Activator("0",0,boolState,"testButton");
//        activators = new ArrayList<>();
//        floatState = new ActivatorState<>(DEFAULT_FLOAT_RAW_STATE, DEFAULT_FLOAT_TYPE);
//        Activator testSlider = new Activator("1",1,floatState,"testSlider");
//        activators.add(testSlider);
//        activators.add(testButton);
//
//        testDevice = new Device(DEFAULT_DEVICE_ID,DEFAULT_DEVICE_NAME,DEFAULT_DEVICE_TYPE,activators
//                , new DummyNetworkHandler("127.0.0.1", 80));
//    }
//
//    @Test
//    public void testSlider(){
//        onView(withText(DEFAULT_DEVICE_TYPE)).perform(click());
//        onView(withText(DEFAULT_DEVICE_NAME)).perform(click());
//    }
//
//
//}
