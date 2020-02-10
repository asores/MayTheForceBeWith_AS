package com.maytheforcebewith.kotlin;

import android.widget.EditText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.maytheforcebewith.kotlin.view.main.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityTest {
    private String filterText = "Dar";
    private String filterTextNotFound = "No such item";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void a_testViewComponentList() {
        try {
            Thread.sleep(5000);
            onView(withId(R.id.rvPeoples)).check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void b_testScrollList() {
        try {
            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples)).check(matches(isDisplayed()));

            Thread.sleep(2000);
            onView(withId(R.id.rvPeoples))
                    .perform(RecyclerViewActions.scrollToPosition(8));

            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples))
                    .perform(RecyclerViewActions.scrollToPosition(19));

            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples))
                    .perform(RecyclerViewActions.scrollToPosition(30));

            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples))
                    .perform(RecyclerViewActions.scrollToPosition(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void c_testSearchListNotFound(){
        try {
            Thread.sleep(2000);
            onView(withId(R.id.action_search)).perform(click());
            onView(isAssignableFrom(EditText.class)).perform(typeText(filterTextNotFound));

            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples)).check(new RecyclerViewItemCountAssertion(0));

            Thread.sleep(3000);
            onView(isAssignableFrom(EditText.class)).perform(pressImeActionButton());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void d_testSearchListFound() {
        try {
            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples)).check(matches(isDisplayed()));

            Thread.sleep(2000);
            onView(withId(R.id.action_search)).perform(click());
            onView(isAssignableFrom(EditText.class)).perform(typeText(filterText));

            Thread.sleep(2000);
            onView(withId(R.id.rvPeoples)).check(new RecyclerViewItemCountAssertion(2));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void e_testClickItemList() {
        try {
            Thread.sleep(4000);
            onView(withId(R.id.rvPeoples)).check(matches(isDisplayed()));

            Thread.sleep(3000);
            onView(withId(R.id.rvPeoples))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
