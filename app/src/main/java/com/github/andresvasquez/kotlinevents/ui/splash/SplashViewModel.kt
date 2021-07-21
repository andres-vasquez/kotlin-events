package com.github.andresvasquez.kotlinevents.ui.splash

import android.app.Application
import com.github.andresvasquez.event_repository.EventFacadeI
import com.github.andresvasquez.kotlinevents.ui.base.BaseViewModel
import com.github.andresvasquez.kotlinevents.ui.base.NavigationCommand

class SplashViewModel(val app: Application, private val eventFacade: EventFacadeI) :
    BaseViewModel(app, eventFacade) {

    fun navigateToTheNextScreen() {
        navigationCommand.value =
            NavigationCommand.To(
                SplashFragmentDirections.actionSplashFragmentToEventListFragment()
            )
    }
}