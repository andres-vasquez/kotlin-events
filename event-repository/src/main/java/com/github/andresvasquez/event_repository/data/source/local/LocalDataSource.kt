package com.github.andresvasquez.event_repository.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingSource
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.exceptions.EventNotFoundException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource constructor(
    private val database: EventDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSourceI {


    override fun getEvents(): PagingSource<Int, EventDTO> {
        return database.eventsDao().getEvents()
    }

    override fun getEventById(eventId: String): LiveData<Result<EventDTO>> {
        return database.eventsDao().getEventById(eventId).map {
            if (it != null) {
                Result.Success(it)
            } else {
                Result.Error(EventNotFoundException("Event not found"))
            }
        }
    }

    override suspend fun insertEvents(events: List<EventDTO>): List<Long> {
        return database.eventsDao().insertEvents(events)
    }

    override suspend fun deleteEvents() {
        database.eventsDao().deleteEvents()
    }

    override suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>) {
        database.remoteKeysDao().insertAll(remoteKey)
    }

    override suspend fun remoteKeysEventId(eventId: Long): RemoteKeys? {
        return database.remoteKeysDao().remoteKeysRepoId(eventId)
    }

    override suspend fun clearRemoteKeys() {
        database.remoteKeysDao().clearRemoteKeys()
    }
}