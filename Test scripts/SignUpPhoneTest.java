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
public class SignUpPhoneTest {
    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<SignUpPhone> rule =
            new ActivityTestRule(SignUpPhone.class, true, false);

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
        rule.launchActivity(intent);
        onView(withId(R.id.editTextPhone1)).perform(typeText("1234567890"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextPhone2)).perform(typeText("1234567890"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonNext)).perform(click());

    }

    @Rule
    public final ActivityTestRule<SignUpPhone> main = new ActivityTestRule<>(SignUpPhone.class);

    @Test
    public void changeText() {
        onView(withId(R.id.textViewPhone)).check(matches(withText("What are your Phone numbers?")));
        onView(withId(R.id.editTextPhone1)).check(matches(withHint("Phone No1")));
        onView(withId(R.id.editTextPhone2)).check(matches(withHint("Phone No2")));


    }

    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonNext)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonNext)).check(matches(withText("Next")));


    }


}