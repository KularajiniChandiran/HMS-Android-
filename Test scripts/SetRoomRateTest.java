package com.example.asus.hotelmanagement;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class SetRoomRateTest {
    @Rule
    public final ActivityTestRule<SetRoomRate> main = new ActivityTestRule<>(SetRoomRate.class);


    @Test
    public void changeText() {
        onView(withId(R.id.textViewRoomType)).check(matches(withText("Room Type")));
        onView(withId(R.id.textViewPrice)).check(matches(withText("Price")));
        onView(withId(R.id.editTextPrice)).perform(typeText("firstname"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonSetRate)).perform(click());
    }


    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonSetRate)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonSetRate)).check(matches(withText("Set Room Rate")));


    }

}