package com.github.andresvasquez.event_repository.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.EventRepositoryI
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.remote.api.ApiStatus
import com.github.andresvasquez.event_repository.model.NextTripSearch
import kotlinx.coroutines.flow.Flow

class FakeTestRepository : EventRepositoryI {
    override fun getNextTripPrefs(): NextTripSearch? {
        TODO("Not yet implemented")
    }

    override fun saveNextTripPrefs(nextTrip: NextTripSearch) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshData() {
        TODO("Not yet implemented")
    }

    override fun getPagingEvents(): Flow<PagingData<EventDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getEventById(eventId: String): Result<EventDTO> {
        TODO("Not yet implemented")
    }

    override val status: LiveData<ApiStatus>
        get() = TODO("Not yet implemented")
}