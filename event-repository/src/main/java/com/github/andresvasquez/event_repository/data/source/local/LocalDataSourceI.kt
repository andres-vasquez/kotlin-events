package com.github.andresvasquez.event_repository.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.data.Result

interface LocalDataSourceI {
    //Events
    fun getEvents(): PagingSource<Int, EventDTO>
    fun getEventById(eventId: String): LiveData<Result<EventDTO>>
    suspend fun insertEvents(events: List<EventDTO>): List<Long>
    suspend fun deleteEvents()

    //Remote keys
    suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>)
    suspend fun remoteKeysEventId(eventId: Long): RemoteKeys?
    suspend fun clearRemoteKeys()
}