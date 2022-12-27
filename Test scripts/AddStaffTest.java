package com.example.asus.hotelmanagement;

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
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class AddStaffTest {
    @Rule
    public final ActivityTestRule<AddStaff> main = new ActivityTestRule<>(AddStaff.class);


    @Test
    public void changeText() {
        onView(withId(R.id.editTextFirstname)).check(matches(withHint("First name")));
        onView(withId(R.id.editTextLastname)).check(matches(withHint("Last name")));
        onView(withId(R.id.editTextNo)).check(matches(withHint("No")));
        onView(withId(R.id.editTextStreet)).check(matches(withHint("Street")));
        onView(withId(R.id.editTextCity)).check(matches(withHint("City")));
        onView(withId(R.id.editTextCountry)).check(matches(withHint("Country")));
        onView(withId(R.id.editTextPhone1)).check(matches(withHint("Phone No1")));
        onView(withId(R.id.editTextPhone2)).check(matches(withHint("Phone No2")));
        onView(withId(R.id.editTextSalary)).check(matches(withHint("Salary")));
        onView(withId(R.id.editTextWorkingHours)).check(matches(withHint("Working Hours")));

        onView(withId(R.id.buttonAdd)).check(matches(withText("Add Staff")));
        onView(withId(R.id.editTextFirstname)).perform(typeText("firstname"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextLastname)).perform(typeText("lastname"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextNo)).perform(typeText("3"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextStreet)).perform(typeText("street"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextCity)).perform(typeText("city"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextCountry)).perform(typeText("country"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextPhone1)).perform(typeText("1234567890"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextPhone2)).perform(typeText("1234567890"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("lastname"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSalary)).perform(typeText("100"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextWorkingHours)).perform(typeText("2.5"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonAdd)).perform(click());




    }


}