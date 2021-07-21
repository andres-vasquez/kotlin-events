package com.github.andresvasquez.event_repository.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.remote.api.ApiStatus
import com.github.andresvasquez.event_repository.model.NextTripSearch
import kotlinx.coroutines.flow.Flow

interface EventRepositoryI {
    fun getNextTripPrefs(): NextTripSearch?
    fun saveNextTripPrefs(nextTrip: NextTripSearch)
    suspend fun refreshData()
    fun getPagingEvents(): Flow<PagingData<EventDTO>>
    fun getEventDetails(eventId: String): LiveData<Result<EventDTO>>

    val status: LiveData<ApiStatus>
}