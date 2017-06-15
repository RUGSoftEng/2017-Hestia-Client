package com.rugged.application.hestia.frontend;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import com.rugged.application.hestia.R;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import hestia.UI.activities.home.HomeActivity;
import hestia.backend.NetworkHandler;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static hestia.UI.activities.login.LoginActivity.hashString;
import static org.junit.Assert.assertEquals;

public class HomeActivityTest {
    private MockServerCollectionsInteractor mockInteractor;

    @Rule
    public IntentsTestRule<HomeActivity> mIntentsRule =
            new IntentsTestRule<>(HomeActivity.class);

    @Before
    public void setUp() {
        SharedPreferences.Editor editor = mIntentsRule.getActivity().getApplication().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        mockInteractor = new MockServerCollectionsInteractor(new NetworkHandler("192.168.178.30", 8000));
        mIntentsRule.getActivity().setServerCollectionsInteractor(mockInteractor);
    }

    @Test
    public void setCredentialsTest() {
        mIntentsRule.getActivity().showChangeCredentialsDialog();
        onView(withId(R.id.newUser)).perform(clearText(), typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.oldPass)).perform(clearText(), typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.newPass)).perform(clearText(), typeText("newpassword"), closeSoftKeyboard());
        onView(withId(R.id.newPassCheck)).perform(clearText(), typeText("newpassword"), closeSoftKeyboard());
        onView(ViewMatchers.withText("Confirm")).perform(click());
        SharedPreferences prefs = mIntentsRule.getActivity().getApplication().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        assertEquals(prefs.getString("username", ""), hashString("newuser"));
        assertEquals(prefs.getString("password", ""), hashString("newpassword"));
    }

    @Test
    public void addDeviceTest() {
        onView(withId(R.id.floating_action_button)).perform(click());
        onView(withId(R.id.collection)).perform(clearText(), typeText("CollectionOne"));
        onView(withId(R.id.pluginName)).perform(clearText(), typeText("PluginOne"));
        onView(ViewMatchers.withText("Confirm")).perform(click());
        onView(ViewMatchers.withHint("ip")).perform(typeText("192.168.178.39"));
        onView(ViewMatchers.withHint("username")).perform(typeText("Gebruiker"));
        onView(ViewMatchers.withText("Confirm")).perform(click());
        assertEquals(mockInteractor.setIP, "192.168.178.39");
        assertEquals(mockInteractor.setUsername, "Gebruiker");
    }

    @After
    public void breakdown() {
        SharedPreferences.Editor editor = mIntentsRule.getActivity().getApplication().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        mIntentsRule.getActivity().setServerCollectionsInteractor(new MockServerCollectionsInteractor(new NetworkHandler("192.168.178.30", 8000)));
    }

}
