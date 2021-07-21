package com.github.andresvasquez.kotlinevents

import android.app.Application
import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.andresvasquez.kotlinevents.ui.MainActivity
import com.github.andresvasquez.kotlinevents.ui.list.EventListAdapter
import com.github.andresvasquez.kotlinevents.utils.DataBindingIdlingResource
import com.github.andresvasquez.kotlinevents.utils.monitorActivity
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class E2EEventTest {
    private lateinit var appContext: Application
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        appContext = ApplicationProvider.getApplicationContext()
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun completeFlow() = runBlockingTest {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Splash
        Espresso.onView(ViewMatchers.withId(R.id.splash_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.instructions))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.motionLayout)).perform(ViewActions.swipeRight())

        //List
        SystemClock.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.events_recycler))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.events_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<EventListAdapter.EventListViewHolder>(
                    0,
                    ViewActions.click()
                )
            )

        //Detail
        SystemClock.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.cover_image))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.event_properties_card))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}