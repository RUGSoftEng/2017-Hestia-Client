package com.rugged.application.hestia;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hestia.UI.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginLogoutTest {
    private final String PACKAGE_NAME = "com.rugged.application.hestia";
    private final String USERNAME = "admin";
    private final String PASSWORD = "password";
    private final String LOGOUT_TEXT = "Logout ";

    /* Instantiate an IntentsTestRule object. */
    @Rule
    public IntentsTestRule<LoginActivity> mIntentsRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void checkLoginLogout() {
        // Logout
        onView(withId(R.id.context_menu)).perform(click());

        onView(withText(LOGOUT_TEXT)).perform(click());

        intended(allOf(
                hasComponent(hasShortClassName("hestia.UI.LoginActivity")),
                toPackage(PACKAGE_NAME)));

        // Login with 'remember me'
        onView(withId(R.id.username))
                .perform(typeText(USERNAME), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.rememberButton)).perform(click());

        onView(withId(R.id.loginButton)).perform(click());

        intended(allOf(
                hasComponent(hasShortClassName("hestia.UI.DeviceListActivity")),
                toPackage(PACKAGE_NAME)));

    }
}
