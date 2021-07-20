package com.github.andresvasquez.event_repository.data.source.remote

import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import com.github.andresvasquez.event_repository.data.Result

interface RemoteDataSourceI {

    suspend fun getEvents(
        city: String, startDateTime: String, endDateTime: String,
        sort: String, genreId: String, size: Int, page: Int
    ): Result<List<EventDetails>>
}