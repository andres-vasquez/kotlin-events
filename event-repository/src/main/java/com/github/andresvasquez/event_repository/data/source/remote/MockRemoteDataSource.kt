package com.github.andresvasquez.event_repository.data.source.remote

import com.github.andresvasquez.event_repository.data.source.remote.model.EventResponse
import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.exceptions.NoEventsFoundException
import com.github.andresvasquez.event_repository.exceptions.RequestErrorException
import com.google.gson.Gson

class MockRemoteDataSource(private val mockJson: String) : RemoteDataSourceI {
    override suspend fun getEvents(
        city: String,
        startDateTime: String,
        endDateTime: String,
        sort: String,
        genreId: String,
        size: Int,
        page: Int
    ): Result<List<EventDetails>> {
        return try {
            val results = Gson().fromJson(mockJson, EventResponse::class.java)
            return if (results.embedded.events.isNullOrEmpty()) {
                Result.Error(NoEventsFoundException("No events found for given parameters"))
            } else {
                Result.Success(results.embedded.events)
            }
        } catch (e: Exception) {
            Result.Error(RequestErrorException(e.toString()))
        }
    }
}