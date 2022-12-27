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
 * Created by ASUS on 06-May-18.
 */
public class SignUpEmailTest {
    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<SignUpEmail> rule =
            new ActivityTestRule(SignUpEmail.class, true, false);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("Firstname", "firstname");
        intent.putExtra("Lastname", "lastname");
        intent.putExtra("No","3");
        intent.putExtra("Street","street");
        intent.putExtra("City","city");
        intent.putExtra("Country","country");
        intent.putExtra("Gender","Male");
        intent.putExtra("Phone1","1234567890");
        intent.putExtra("Phone2","1453216789");
        rule.launchActivity(intent);
        onView(withId(R.id.editTextEmail)).perform(typeText("email"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonNext)).perform(click());
    }

    @Rule
    public final ActivityTestRule<SignUpEmail> main = new ActivityTestRule<>(SignUpEmail.class);

    @Test
    public void changeText() {
        onView(withId(R.id.textViewEmail)).check(matches(withText("Enter your email address")));
        onView(withId(R.id.editTextEmail)).check(matches(withHint("Email address")));

    }


    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonNext)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonNext)).check(matches(withText("Next")));


    }

}