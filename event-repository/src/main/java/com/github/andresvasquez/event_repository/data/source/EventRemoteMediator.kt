package com.github.andresvasquez.event_repository.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.local.LocalDataSourceI
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.data.source.prefs.PrefsDataSourceI
import com.github.andresvasquez.event_repository.data.source.remote.RemoteDataSourceI
import com.github.andresvasquez.event_repository.data.toEventDto
import com.github.andresvasquez.event_repository.utils.Constants
import com.github.andresvasquez.event_repository.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val prefs: PrefsDataSourceI,
    private val local: LocalDataSourceI,
    private val remote: RemoteDataSourceI
) : RemoteMediator<Int, EventDTO>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventDTO>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {
            val nextTripSearch = prefs.getNextTripPrefs()
            val apiResponse = nextTripSearch?.let {
                remote.getEvents(
                    city = it.city,
                    startDateTime = nextTripSearch.startDateTime,
                    endDateTime = nextTripSearch.endDateTime,
                    genreId = nextTripSearch.genreId,
                    sort = Constants.API_SORT,
                    size = Constants.API_PAGE_SIZE,
                    page = page
                )
            }

            if (apiResponse is Result.Success) {
                val events = apiResponse.data
                val endOfPaginationReached = events.isEmpty()

                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    local.clearRemoteKeys()
                    local.deleteEvents()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val idsIInLocalDb = local.insertEvents(events.toEventDto().toList())

                val keys = idsIInLocalDb.map {
                    RemoteKeys(eventId = it, prevKey = prevKey, nextKey = nextKey)
                }

                local.insertAllRemoteKeys(keys)

                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else if (apiResponse is Result.Error) {
                return MediatorResult.Error(apiResponse.exception)
            } else {
                return MediatorResult.Error(NullPointerException())
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, EventDTO>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { eventId ->
                local.remoteKeysEventId(eventId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, EventDTO>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { event ->
                // Get the remote keys of the first items retrieved
                local.remoteKeysEventId(event.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, EventDTO>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { event ->
                // Get the remote keys of the last item retrieved
                local.remoteKeysEventId(event.id)
            }
    }
}