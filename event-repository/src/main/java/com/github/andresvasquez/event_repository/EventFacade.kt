package com.github.andresvasquez.event_repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.paging.map
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.error
import com.github.andresvasquez.event_repository.data.source.EventRepositoryI
import com.github.andresvasquez.event_repository.data.source.remote.api.ApiStatus
import com.github.andresvasquez.event_repository.data.toEventDetailDomain
import com.github.andresvasquez.event_repository.data.toEventListDomain
import com.github.andresvasquez.event_repository.model.EventDetailDomain
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.event_repository.model.NextTripSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventFacade(private val repository: EventRepositoryI) : EventFacadeI {
    override val status: LiveData<ApiStatus> = repository.status

    override fun saveNextTripPrefs(nextTrip: NextTripSearch) {
        repository.saveNextTripPrefs(nextTrip)
    }

    override fun getNextTripPrefs(): NextTripSearch? {
        return repository.getNextTripPrefs()
    }

    override suspend fun refreshData() {
        repository.refreshData()
    }

    override fun getPagingEvents(): Flow<PagingData<EventListDomain>> {
        return repository.getPagingEvents()
            .map { pagingData -> pagingData.map { it.toEventListDomain() } }
    }

    override fun getEventDetails(id: String): LiveData<Result<EventDetailDomain>> {
        return repository.getEventDetails(id).map {
            if (it is Result.Success) {
                Result.Success(it.data.toEventDetailDomain())
            } else {
                Result.Error(it.error!!)
            }
        }
    }
}