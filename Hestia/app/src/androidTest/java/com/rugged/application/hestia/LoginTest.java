package com.rugged.application.hestia;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hestia.UI.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    private static final String PACKAGE_NAME = "com.rugged.application.hestia";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    /* Instantiate an IntentsTestRule object. */
    @Rule
    public IntentsTestRule<LoginActivity> mIntentsRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void verifyMessageSentToMessageActivity() {

        // Types a message into a EditText element.
        onView(withId(R.id.username))
                .perform(typeText(USERNAME), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());

        // Clicks a button to send the message to another
        // activity through an explicit intent.
        onView(withId(R.id.loginButton)).perform(click());

        // Verifies that the DisplayMessageActivity received an intent
        // with the correct package name and message.
        intended(allOf(
                hasComponent(hasShortClassName(".DeviceListActivity")),
                toPackage(PACKAGE_NAME)));
    }

}
