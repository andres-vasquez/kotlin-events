package com.github.andresvasquez.event_repository.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDAO {
    @Query("SELECT * FROM event ORDER BY startDate ASC")
    fun getEvents(): PagingSource<Int, EventDTO>

    @Query("SELECT * FROM event WHERE eventId = :eventId")
    fun getEventById(eventId: String): LiveData<EventDTO?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventDTO>): List<Long>

    @Query("DELETE FROM event")
    suspend fun deleteEvents()
}