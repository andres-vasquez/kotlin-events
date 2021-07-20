package com.github.andresvasquez.event_repository.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.paging.PagingSource

@Dao
interface EventDAO {
    @Query("SELECT * FROM event ORDER BY startDate ASC")
    fun getEvents(): PagingSource<Int, EventDTO>

    @Query("SELECT * FROM event WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventDTO>)

    @Query("DELETE FROM event")
    suspend fun deleteEvents()
}