package com.rugged.application.hestia.UI.dialogs;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

/**

@RunWith(AndroidJUnit4.class)
public class SetIpTest {
    private final String SET_IP_TEXT = "Set IP ";
    private final String TEST_IP = "192.168.0.1";

    @Rule
    public IntentsTestRule<DeviceListActivity> mIntentsRule =
            new IntentsTestRule<>(DeviceListActivity.class);

    @Before
    public void checkDialog() {
        onView(ViewMatchers.withId(R.id.context_menu)).perform(click());
        onView(withText(SET_IP_TEXT)).perform(click());
    }

    @Test
    public void checkEnterIp(){
        onView(withId(R.id.ip)).perform(clearText(),typeText(TEST_IP), closeSoftKeyboard());

        onView(withId(R.id.confirm_button)).perform(click());

        assertEquals(ServerCollectionsInteractor.getInstance().getIp(),TEST_IP);
    }


}

 */