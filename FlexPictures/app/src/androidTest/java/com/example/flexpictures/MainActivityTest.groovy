package com.example.flexpictures

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4.class)
class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void cardViewTest() {

        Espresso.onView(withId(R.id.card_stack_view)).perform(ViewActions.swipeUp())
        Espresso.onView(withId(R.id.card_stack_view)).perform(ViewActions.swipeDown())
        Espresso.onView(withId(R.id.card_stack_view)).perform(ViewActions.swipeLeft())
        Espresso.onView(withId(R.id.card_stack_view)).perform(ViewActions.swipeRight())
    }
}
