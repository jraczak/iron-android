package com.justinraczak.android.squadgoals;


import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xamarin.testcloud.espresso.Factory;
import com.xamarin.testcloud.espresso.ReportHelper;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;




@LargeTest
@RunWith(AndroidJUnit4.class)
public class BasicTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();

    @Test
    public void sampleTest() {

        reportHelper.label("Given I accept contacts permissions");
        allowPermissionsIfNeeded();

        reportHelper.label("Then I should see the app name");
        ViewInteraction textView = onView(
                allOf(withText("Squad Goals"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Squad Goals")));

        reportHelper.label("And I should see the email sign in button");
        ViewInteraction button = onView(
                allOf(withId(R.id.email_sign_in_button),
                        childAtPosition(
                                allOf(withId(R.id.email_login_form),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                2),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

    }

    @Test
    public void sampleEmailLoginTest() {
        reportHelper.label("Given I accept contacts permissions");
        allowPermissionsIfNeeded();

        reportHelper.label("When I type in my email and password");
        onView(withId(R.id.email))
                .perform(typeText("jraczak@gmail.com"));
        onView(withId(R.id.password))
                .perform(typeText("password"));

        reportHelper.label("And I click the sign in with email button");
        onView(withId(R.id.email_sign_in_button))
                .perform(click());

        reportHelper.label("Then I should see the sign out button on the dashboard");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Log.d("Login test", "thread was interrupted");
        }
        //onView(withId(R.id.button_sign_out))
        onView(withId(R.id.fab_new_workout))
                .check(matches(isDisplayed()));
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

    private void allowPermissionsIfNeeded()  {
        //  mDevice = UiDevice.getInstance(getInstrumentation());
        if (Build.VERSION.SDK_INT >= 23) {
            UiObject allowPermissions = UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().text("ALLOW"));
            if (allowPermissions.exists()) {
                try {
                    Log.d("acceptPermissionScript", "Trying to click accept dialog");
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    Log.e(e.toString(), "There is no permissions dialog to interact with ");
                }
            }
        }
    }

    //public static void acceptCurrentPermission(UiDevice device) throws UiObjectNotFoundException {
    //    UiObject denyButton = device.findObject(new UiSelector().text("Allow"));
    //    denyButton.click();
    //}

    @After
    public void TearDown() {
        reportHelper.label("Stopping app");
    }
}
