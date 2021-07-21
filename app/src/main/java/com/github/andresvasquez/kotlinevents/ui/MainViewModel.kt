package com.github.andresvasquez.kotlinevents.ui

import android.app.Application
import com.github.andresvasquez.event_repository.EventFacadeI
import com.github.andresvasquez.event_repository.model.NextTripSearch
import com.github.andresvasquez.kotlinevents.ui.base.BaseViewModel

class MainViewModel(val app: Application, private val eventFacade: EventFacadeI) :
    BaseViewModel(app, eventFacade) {

    fun saveSearch(nextTripSearch: NextTripSearch) {
        this.eventFacade.saveNextTripPrefs(nextTripSearch)
    }

    fun getSearch(): NextTripSearch? {
        return this.eventFacade.getNextTripPrefs()
    }
}