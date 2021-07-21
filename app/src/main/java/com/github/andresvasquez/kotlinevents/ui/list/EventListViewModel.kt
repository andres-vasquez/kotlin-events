package com.github.andresvasquez.kotlinevents.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.andresvasquez.event_repository.EventFacadeI
import com.github.andresvasquez.event_repository.data.source.remote.api.ApiStatus
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.kotlinevents.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class EventListViewModel(val app: Application, private val eventFacade: EventFacadeI) :
    BaseViewModel(app, eventFacade) {

    val status: LiveData<ApiStatus> = eventFacade.status
    private var currentSearchResult: Flow<PagingData<EventListDomain>>? = null

    fun loadEvents(): Flow<PagingData<EventListDomain>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }

        val newResult: Flow<PagingData<EventListDomain>> = eventFacade.getPagingEvents()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}