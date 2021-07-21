package com.github.andresvasquez.event_repository.data.source.local

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

    override suspend fun getEventById(eventId: String): Result<EventDTO> =
        withContext(ioDispatcher) {
            try {
                val event = database.eventsDao().getEventById(eventId)
                if (event != null) {
                    return@withContext Result.Success(event)
                } else {
                    return@withContext Result.Error(EventNotFoundException("Event not found!"))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
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