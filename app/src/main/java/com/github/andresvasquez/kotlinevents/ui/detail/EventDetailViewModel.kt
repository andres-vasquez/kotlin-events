package com.github.andresvasquez.kotlinevents.ui.detail

import android.app.Application
import com.github.andresvasquez.event_repository.EventFacadeI
import com.github.andresvasquez.kotlinevents.ui.base.BaseViewModel

class EventDetailViewModel(val app: Application, private val eventFacade: EventFacadeI) :
    BaseViewModel(app, eventFacade) {
}