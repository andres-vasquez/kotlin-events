package com.github.andresvasquez.event_repository.repository.fake

import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.remote.RemoteDataSourceI
import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import com.github.andresvasquez.event_repository.exceptions.NoEventsFoundException
import com.github.andresvasquez.event_repository.utils.Constants

class FakeRemoteDataSource(private val events: List<EventDetails> = mutableListOf()) :
    RemoteDataSourceI {

    override suspend fun getEvents(
        city: String,
        startDateTime: String,
        endDateTime: String,
        sort: String,
        genreId: String,
        size: Int,
        page: Int
    ): Result<List<EventDetails>> {
        return if (city == Constants.DEFAULT_NEXT_TRIP.city &&
            startDateTime == Constants.DEFAULT_NEXT_TRIP.startDateTime &&
            endDateTime == Constants.DEFAULT_NEXT_TRIP.endDateTime
        ) {
            Result.Success(events)
        } else {
            Result.Error(NoEventsFoundException("No events for given search"))
        }
    }
}