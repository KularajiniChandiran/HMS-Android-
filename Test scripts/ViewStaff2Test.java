package com.example.asus.hotelmanagement;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class ViewStaff2Test {
        /*@Rule
        public final ActivityTestRule<ViewStaff2> main = new ActivityTestRule<>(ViewStaff2.class);


        @Test
        public void changeText() {
            onView(withId(R.id.textViewFirstname)).check(matches(withText("First name")));
            onView(withId(R.id.textViewLastname)).check(matches(withText("Last name")));
            onView(withId(R.id.textViewNo)).check(matches(withText("No")));
            onView(withId(R.id.textViewStreet)).check(matches(withText("Street")));
            onView(withId(R.id.textViewCity)).check(matches(withText("City")));
            onView(withId(R.id.textViewCountry)).check(matches(withText("Country")));
            onView(withId(R.id.textViewPhone1)).check(matches(withText("Phone No1")));
            onView(withId(R.id.textViewPhone2)).check(matches(withText("Phone No2")));
            onView(withId(R.id.textViewGender)).check(matches(withText("Gender")));
            onView(withId(R.id.textViewSalary)).check(matches(withText("Salary")));
            onView(withId(R.id.textViewWorkingHours)).check(matches(withText("Working_Hours")));
        }

        @Test
        public void ensureButtonClick(){
            // press the button.
            onView(withId(R.id.buttonClose)).check(matches(notNullValue() ));
            onView(withId(R.id.buttonClose)).check(matches(withText("Close")));
            onView(withId(R.id.buttonClose)).perform(click());

        }*/

}