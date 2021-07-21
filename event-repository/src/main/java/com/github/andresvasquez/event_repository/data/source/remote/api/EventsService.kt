package com.github.andresvasquez.event_repository.data.source.remote.api

import com.github.andresvasquez.event_repository.data.source.remote.model.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {
    @GET("/discovery/v2/events")
    suspend fun getEvents(
        @Query("city") city: String,
        @Query("startDateTime") startDateTime: String,
        @Query("endDateTime") endDateTime: String,
        @Query("sort") sort: String,
        @Query("genreId") genreId: String,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): EventResponse
}