package com.example.asus.hotelmanagement;



import android.support.test.espresso.ViewInteraction;

import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import android.test.suitebuilder.annotation.LargeTest;

import android.view.View;
import android.view.ViewGroup;

import android.view.ViewParent;

import org.hamcrest.Description;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import org.junit.Rule;

import org.junit.Test;

import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.Espresso.pressBack;

import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;

import static android.support.test.espresso.action.ViewActions.pressImeActionButton;

import static android.support.test.espresso.action.ViewActions.replaceText;

import static android.support.test.espresso.action.ViewActions.scrollTo;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.matcher.ViewMatchers.withClassName;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;



@LargeTest

@RunWith(AndroidJUnit4.class)

public class StaffEntryTest {

    
	@Rule
    
	public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    

	@Test
    
	public void staffEntry2() {
        
	ViewInteraction appCompatEditText = onView(
                
		allOf(withId(R.id.editTextEmail),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
0),
1)));
        
	appCompatEditText.perform(scrollTo(), replaceText("rani@gmail.com"), closeSoftKeyboard());

 
      
 	ViewInteraction appCompatEditText2 = onView(
                
		allOf(withId(R.id.editTextEmail), withText("rani@gmail.com"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
0),
1)));
        
	appCompatEditText2.perform(pressImeActionButton());

 
       
	ViewInteraction appCompatEditText3 = onView(
                
		allOf(withId(R.id.editTextPassword),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
0),
2)));
        
	appCompatEditText3.perform(scrollTo(), replaceText("hello123"), closeSoftKeyboard());

        

	ViewInteraction appCompatEditText4 = onView(
                
		allOf(withId(R.id.editTextPassword), withText("hello123"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
0),
2)));
        

	appCompatEditText4.perform(pressImeActionButton());

        

	ViewInteraction appCompatButton = onView(
                
		allOf(withId(R.id.buttonLogin), withText("Login"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),0),
3)));
        
	appCompatButton.perform(scrollTo(), click());

        

	ViewInteraction appCompatButton2 = onView(
                
		allOf(withId(R.id.buttonStaff), withText("Staff"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
0),
2),
isDisplayed()));
        
	appCompatButton2.perform(click());

        

	ViewInteraction appCompatButton3 = onView(
                
		allOf(withId(R.id.buttonAdd), withText("Add Staff"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
1),
0),isDisplayed()));
        
	appCompatButton3.perform(click());

        

	pressBack();

        
	pressBack();

        

	ViewInteraction appCompatButton4 = onView(
                
		allOf(withId(R.id.buttonView), withText("View Details"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
1),
1),isDisplayed()));
        
	appCompatButton4.perform(click());

        

	pressBack();

        
	ViewInteraction appCompatButton5 = onView(
                
		allOf(withId(R.id.buttonUpdate), withText("Update Detail"),
                        
			childAtPosition(
                                
				childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
1),
2),
isDisplayed()));
        
	appCompatButton5.perform(click());

        

	pressBack();

        

	ViewInteraction appCompatButton6 = onView(
                
		allOf(withId(R.id.buttonRemove), withText("Remove"),
                        
			childAtPosition(
		
      		childAtPosition(
                                        
					withClassName(is("android.widget.RelativeLayout")),
1),3),
isDisplayed()));
        
	appCompatButton6.perform(click());

        
	pressBack();

    
	}

    

private static Matcher<View> childAtPosition(
            
	final Matcher<View> parentMatcher, final int position) {

        
	return new TypeSafeMatcher<View>() {
            
	@Override
           
	public void describeTo(Description description) {
                
		description.appendText("Child at position " + position + " in parent ");
               
		parentMatcher.describeTo(description);
            
	}

            

	@Override
            
	public boolean matchesSafely(View view) {
                
		ViewParent parent = view.getParent();
                
		return parent instanceof ViewGroup && parentMatcher.matches(parent)
 && view.equals(((ViewGroup) parent).getChildAt(position));
            
	}
        
};
    
}

}
