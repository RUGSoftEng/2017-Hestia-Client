package com.rugged.application.hestia;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hestia.UI.DeviceListActivity;
import hestia.backend.BackendInteractor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SetIpTest {
    private final String SET_IP_TEXT = "Set IP ";
    private final String TEST_IP = "192.168.0.1";

    @Rule
    public IntentsTestRule<DeviceListActivity> mIntentsRule =
            new IntentsTestRule<>(DeviceListActivity.class);

    @Before
    public void checkDialog() {
        onView(withId(R.id.context_menu)).perform(click());

        onView(withText(SET_IP_TEXT)).perform(click());

        onView(withText(R.string.ipDialogText)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEnterIp(){
        onView(withId(R.id.ip)).perform(typeText(TEST_IP), closeSoftKeyboard());

        onView(withId(R.id.confirm_button)).perform(click());

        assert(BackendInteractor.getInstance().getIp()==TEST_IP);
    }


}
