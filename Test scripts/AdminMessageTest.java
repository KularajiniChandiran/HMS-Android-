package com.example.asus.hotelmanagement;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class AdminMessageTest {
    @Rule
    public final ActivityTestRule<AdminMessage> main = new ActivityTestRule<>(AdminMessage.class);

    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonRoom)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonRoom)).check(matches(withText("Room")));
        onView(withId(R.id.buttonRoom)).perform(click());

        onView(withId(R.id.buttonGuest)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonGuest)).check(matches(withText("Guest")));
        onView(withId(R.id.buttonGuest)).perform(click());

        onView(withId(R.id.buttonStaff)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonStaff)).check(matches(withText("Staff")));
        onView(withId(R.id.buttonStaff)).perform(click());

        onView(withId(R.id.buttonMessage)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonMessage)).check(matches(withText("Message")));
        onView(withId(R.id.buttonMessage)).perform(click());

        onView(withId(R.id.buttonLogout)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonLogout)).check(matches(withText("Logout")));
        onView(withId(R.id.buttonLogout)).perform(click());



    }

}