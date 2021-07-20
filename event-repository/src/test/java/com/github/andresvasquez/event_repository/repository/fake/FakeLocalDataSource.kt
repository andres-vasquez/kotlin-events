package com.github.andresvasquez.event_repository.repository.fake

import androidx.paging.PagingSource
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.local.LocalDataSourceI
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.exceptions.EventNotFoundException

class FakeLocalDataSource(private val events: MutableList<EventDTO> = mutableListOf()) :
    LocalDataSourceI {
    override fun getEvents(): PagingSource<Int, EventDTO> {
        return FakePagingEvents(events)
    }

    override suspend fun getEventById(eventId: String): Result<EventDTO> {
        val event = this.events.find { it.id === eventId }
        return if (event != null) {
            Result.Success(event)
        } else {
            Result.Error(EventNotFoundException("Event not found"))
        }
    }

    override suspend fun insertEvents(events: List<EventDTO>) {
        this.events.addAll(events)
    }

    override suspend fun deleteEvents() {
        this.events.clear()
    }

    override suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>) {
        TODO("Not yet implemented")
    }

    override suspend fun remoteKeysEventId(eventId: String): RemoteKeys? {
        TODO("Not yet implemented")
    }

    override suspend fun clearRemoteKeys() {
        TODO("Not yet implemented")
    }
}