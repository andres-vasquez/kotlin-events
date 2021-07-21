package com.github.andresvasquez.kotlinevents

import android.app.Application
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.kotlinevents.ui.list.EventListAdapter
import com.github.andresvasquez.kotlinevents.ui.list.EventListFragment
import com.github.andresvasquez.kotlinevents.ui.list.EventListFragmentDirections
import com.github.andresvasquez.kotlinevents.ui.splash.SplashFragment
import com.github.andresvasquez.kotlinevents.ui.splash.SplashFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class FragmentScreenTest {
    private lateinit var appContext: Application

    @Before
    fun init() {
        appContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun displaySplashPage() = runBlockingTest {
        // GIVE - the app is running
        // When - SplashFragment is displayed
        val scenario =
            launchFragmentInContainer<SplashFragment>(Bundle(), R.style.Theme_KotlinEvents)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        //Then
        Espresso.onView(withId(R.id.splash_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.instructions))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displayList() = runBlockingTest {
        // GIVE - the app is running
        val scenario =
            launchFragmentInContainer<SplashFragment>(Bundle(), R.style.Theme_KotlinEvents)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // When - SplashFragment is displayed
        Espresso.onView(ViewMatchers.withText(appContext.getString(R.string.splash_title)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(appContext.getString(R.string.instructions)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Simulate the motion layout
        SystemClock.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.motionLayout)).perform(ViewActions.swipeRight())
        SystemClock.sleep(2000)

        // Then: Review the list is present and it has rows
        Mockito.verify(navController)
            .navigate(SplashFragmentDirections.actionSplashFragmentToEventListFragment())
    }

    @Test
    fun displayDetail() = runBlockingTest {
        // GIVE - the app is running and the list is displayed
        val events = mutableListOf<EventListDomain>()
        val scenario =
            launchFragmentInContainer<EventListFragment>(Bundle(), R.style.Theme_KotlinEvents)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // When - ListFragment is displayed
        Espresso.onView(withId(R.id.events_recycler))
            .perform(actionOnItemAtPosition<EventListAdapter.EventListViewHolder>(0, click()))
        SystemClock.sleep(2000)
        scenario.onFragment {
            events.add(it.adapter.getItemByPosition(0)!!)
        }

        verify(navController).navigate(
            EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(
                events.first()
            )
        )
    }
}