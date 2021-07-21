package com.github.andresvasquez.kotlinevents.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import com.github.andresvasquez.event_repository.EventFacadeI
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.model.EventDetailDomain
import com.github.andresvasquez.kotlinevents.ui.base.BaseViewModel

class EventDetailViewModel(val app: Application, private val eventFacade: EventFacadeI) :
    BaseViewModel(app, eventFacade) {

    fun getEvent(id: String): LiveData<Result<EventDetailDomain>> {
        return eventFacade.getEventDetails(id)
    }
}