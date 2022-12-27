package com.example.asus.hotelmanagement;

import static org.junit.Assert.*;

/**
 * Created by ASUS on 06-May-18.
 */

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static org.hamcrest.core.IsNull.notNullValue;
public class SignUpTest {
    @Rule
    public final ActivityTestRule<SignUp> main = new ActivityTestRule<>(SignUp.class);


    @Test
    public void changeText() {
        onView(withId(R.id.textViewName)).check(matches(withText("What is your name?")));
        onView(withId(R.id.editTextFirstname)).check(matches(withHint("First name")));
        onView(withId(R.id.editTextLastname)).check(matches(withHint("Last name")));
        onView(withId(R.id.editTextFirstname)).perform(typeText("firstname"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextLastname)).perform(typeText("lastname"),
                closeSoftKeyboard());

    }


    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonNext)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonNext)).check(matches(withText("Next")));
        onView(withId(R.id.buttonNext)).perform(click());

    }



}