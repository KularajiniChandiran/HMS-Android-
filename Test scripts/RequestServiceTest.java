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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by ASUS on 07-May-18.
 */
public class RequestServiceTest {
    @Rule
    public final ActivityTestRule<RequestService> main = new ActivityTestRule<>(RequestService.class);

    @Test
    public void changeText() {
        onView(withId(R.id.editTextRoom_no)).check(matches(withHint("Room no")));
        onView(withId(R.id.editTextRoom_no)).perform(typeText("firstname"),
                closeSoftKeyboard());
    }


    @Test
    public void ensureButtonClick(){
        // press the button.
        onView(withId(R.id.buttonRequest)).check(matches(notNullValue() ));
        onView(withId(R.id.buttonRequest)).check(matches(withText("Request")));
        //onView(withId(R.id.buttonRequest)).perform(click());

    }
}