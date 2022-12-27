package com.example.asus.hotelmanagement;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 06-May-18.
 */
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static android.support.test.espresso.action.ViewActions.click;
@RunWith(AndroidJUnit4.class)
public class SignUpAddressTest {
    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<SignUpAddress> rule =
            new ActivityTestRule(SignUpAddress.class, true, false);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("Firstname", "firstname");
        intent.putExtra("Lastname", "lastname");
        rule.launchActivity(intent);
        onView(withId(R.id.editTextNo)).perform(typeText("3"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextStreet)).perform(typeText("street"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextCity)).perform(typeText("city"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextCountry)).perform(typeText("country"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonNext)).perform(click());

    }

    @Rule
    public final ActivityTestRule<SignUpAddress> main = new ActivityTestRule<>(SignUpAddress.class);

    @Test
    public void changeText() {
        onView(withId(R.id.textViewAddress)).check(matches(withText("What is your address?")));
        onView(withId(R.id.editTextNo)).check(matches(withHint("No")));
        onView(withId(R.id.editTextStreet)).check(matches(withHint("Street")));
        onView(withId(R.id.editTextCity)).check(matches(withHint("City")));
        onView(withId(R.id.editTextCountry)).check(matches(withHint("Country")));
        onView(withId(R.id.buttonNext)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonNext)).check(matches(withText("Next")));
    }


}