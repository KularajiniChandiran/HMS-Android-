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
public class BookRoomTest {
    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<BookRoom> rule =
            new ActivityTestRule(BookRoom.class, true, false);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("email", "ppp@gmail.com");
        intent.putExtra("check_in", "2018-08-10");
        intent.putExtra("check_out", "2018-08-11");
        intent.putExtra("room_no", "1");
        rule.launchActivity(intent);
        onView(withId(R.id.editTextAdultsNo)).perform(typeText("1"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextChildrenNo)).perform(typeText("1"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonReserve)).perform(click());
    }



    @Rule
    public final ActivityTestRule<BookRoom> main = new ActivityTestRule<>(BookRoom.class);

    @Test
    public void changeText() {
        onView(withId(R.id.editTextAdultsNo)).check(matches(withHint("No of Adults")));
        onView(withId(R.id.editTextChildrenNo)).check(matches(withHint("No of Children")));
        onView(withId(R.id.buttonReserve)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonReserve)).check(matches(withText("Reserve")));


    }


}