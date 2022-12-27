package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by ASUS on 06-May-18.
 */
public class SignUpGenderTest {
    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<SignUpGender> rule =
            new ActivityTestRule(SignUpGender.class, true, false);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("Firstname", "firstname");
        intent.putExtra("Lastname", "lastname");
        intent.putExtra("No",3);
        intent.putExtra("Street","street");
        intent.putExtra("City","city");
        intent.putExtra("Country","country");
        rule.launchActivity(intent);
        onView(withId(R.id.buttonNext)).perform(click());
    }

    @Rule
    public final ActivityTestRule<SignUpGender> main = new ActivityTestRule<>(SignUpGender.class);

    @Test
    public void changeText() {
        onView(withId(R.id.textViewGender)).check(matches(withText("What is your gender?")));
    }

    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonNext)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonNext)).check(matches(withText("Next")));


    }

}