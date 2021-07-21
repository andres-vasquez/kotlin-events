package com.github.andresvasquez.event_repository.repository.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.local.LocalDataSourceI
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.exceptions.EventNotFoundException

class FakeLocalDataSource(
    private val events: MutableList<EventDTO> = mutableListOf(),
    private val keys: MutableList<RemoteKeys> = mutableListOf()
) :
    LocalDataSourceI {
    override fun getEvents(): PagingSource<Int, EventDTO> {
        return FakePagingEvents(events)
    }

    override fun getEventById(eventId: String): LiveData<Result<EventDTO>> {
        val resultLiveData = MutableLiveData<Result<EventDTO>>()
        val event = this.events.find { it.eventId == eventId }
        if (event != null) {
            resultLiveData.postValue(Result.Success(event))
        } else {
            resultLiveData.postValue(Result.Error(EventNotFoundException("Not found: " + eventId)))
        }
        return resultLiveData
    }

    override suspend fun insertEvents(events: List<EventDTO>): List<Long> {
        this.events.addAll(events)
        return this.events.map { it.id }
    }


    override suspend fun deleteEvents() {
        this.events.clear()
    }

    override suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>) {
        this.keys.addAll(remoteKey)
    }

    override suspend fun remoteKeysEventId(eventId: Long): RemoteKeys? {
        return this.keys.find { it.eventId == eventId }
    }

    override suspend fun clearRemoteKeys() {
        this.keys.clear()
    }
}