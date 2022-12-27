package com.example.asus.hotelmanagement;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class ReceptionistPageTest {
    @Rule
    public final ActivityTestRule<ReceptionistPage> main = new ActivityTestRule<>(ReceptionistPage.class);


    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonService)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonService)).check(matches(withText("Service")));
        onView(withId(R.id.buttonService)).perform(click());

        onView(withId(R.id.buttonLogout)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonLogout)).check(matches(withText("Logout")));
        onView(withId(R.id.buttonLogout)).perform(click());


    }

}