package com.github.andresvasquez.event_repository.data.source.remote

import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.remote.api.EventsApi

class RemoteDataSource(apiKey: String) : RemoteDataSourceI {
    init {
        EventsApi.setApiKey(apiKey)
    }

    override suspend fun getEvents(
        city: String,
        startDateTime: String,
        endDateTime: String,
        sort: String,
        genreId: String,
        size: Int,
        page: Int
    ): Result<List<EventDetails>> {
        TODO("Not yet implemented")
    }

}