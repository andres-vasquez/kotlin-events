package com.github.andresvasquez.event_repository.data.source.remote

import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.remote.api.EventsApi
import com.github.andresvasquez.event_repository.exceptions.EventException
import com.github.andresvasquez.event_repository.exceptions.NoEventsFoundException
import com.github.andresvasquez.event_repository.exceptions.ServerErrorException
import retrofit2.HttpException

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
        return try {
            val response = EventsApi.retrofitService.getEvents(
                city,
                startDateTime,
                endDateTime,
                sort,
                genreId,
                size,
                page
            )
            return if (response.embedded.events.isNullOrEmpty()) {
                Result.Error(NoEventsFoundException("No events available"))
            } else {
                Result.Success(response.embedded.events)
            }
        } catch (e: HttpException) {
            Result.Error(ServerErrorException(e.toString()))
        } catch (e: Exception) {
            Result.Error(EventException(e.toString()))
        }
    }
}