package com.example.asus.hotelmanagement;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
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
public class LoginTest {
    @Rule
    public final ActivityTestRule<Login> main = new ActivityTestRule<>(Login.class);


    @Test
    public void changeText() {
        onView(withId(R.id.textViewForgotpassword)).check(matches(withText("------CREATE NEW ACCOUNT------")));
        onView(withId(R.id.editTextEmail)).check(matches(withHint("Email address")));
        onView(withId(R.id.editTextPassword)).check(matches(withHint("Password")));
        onView(withId(R.id.editTextPassword)).perform(typeText("firstname"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("lastname"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonLogin)).perform(click());

    }


    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonLogin)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonLogin)).check(matches(withText("Login")));

        onView(withId(R.id.buttonSignup)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonSignup)).check(matches(withText("SignUp")));
        onView(withId(R.id.buttonSignup)).perform(scrollTo()).perform(click());

    }


}