package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class CancelBookingTest {
    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<CancelBooking> rule =
            new ActivityTestRule(CancelBooking.class, true, false);

    @Test
    public void changeText() {
        Intent intent = new Intent();
        intent.putExtra("Email", "ppp@gmail.com");
        rule.launchActivity(intent);
        onView(withId(R.id.editTextRoomno)).perform(typeText("1"),
                closeSoftKeyboard());

        onView(withId(R.id.buttonCancel1)).perform(click());
    }

    /*@Rule
    public final ActivityTestRule<CancelBooking> main = new ActivityTestRule<>(CancelBooking.class);

    @Test
    public void ensureButtonClick(){
        //press the button.
        onView(withId(R.id.editTextRoomno)).check(matches(withHint("Room no")));

        onView(withId(R.id.buttonCancel1)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonCancel)).check(matches(withText("Cancel")));

        onView(withId(R.id.buttonRoom)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonRoom)).check(matches(withText("Room")));
        onView(withId(R.id.buttonRoom)).perform(click());

        onView(withId(R.id.buttonService)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonService)).check(matches(withText("Service")));
        onView(withId(R.id.buttonService)).perform(click());

        onView(withId(R.id.buttonCancel)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonCancel)).check(matches(withText("Cancel")));
        onView(withId(R.id.buttonCancel)).perform(click());

        onView(withId(R.id.buttonMessage)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonMessage)).check(matches(withText("Message")));
        onView(withId(R.id.buttonMessage)).perform(click());

        /*onView(withId(R.id.buttonLogout)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonLogout)).check(matches(withText("Logout")));
        //onView(withId(R.id.buttonLogout)).perform(click());



    }*/

}