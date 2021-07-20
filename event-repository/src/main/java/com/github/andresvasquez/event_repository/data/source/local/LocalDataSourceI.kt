package com.github.andresvasquez.event_repository.data.source.local

import androidx.paging.PagingSource
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.data.Result

interface LocalDataSourceI {
    //Events
    fun getEvents(): PagingSource<Int, EventDTO>
    suspend fun getEventById(eventId: String): Result<EventDTO>
    suspend fun insertEvents(events: List<EventDTO>)
    suspend fun deleteEvents()

    //Remote keys
    suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>)
    suspend fun remoteKeysEventId(eventId: String): RemoteKeys?
    suspend fun clearRemoteKeys()
}