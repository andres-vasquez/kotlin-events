package com.github.andresvasquez.event_repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.remote.api.ApiStatus
import com.github.andresvasquez.event_repository.model.EventDetailDomain
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.event_repository.model.NextTripSearch
import kotlinx.coroutines.flow.Flow

interface EventFacadeI {
    fun saveNextTripPrefs(nextTrip: NextTripSearch)
    suspend fun refreshData()
    fun getPagingEvents(): Flow<PagingData<EventListDomain>>
    suspend fun getEventDetails(id: String): Result<EventDetailDomain>
    val status: LiveData<ApiStatus>
}