package com.github.andresvasquez.event_repository.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.local.LocalDataSourceI
import com.github.andresvasquez.event_repository.data.source.prefs.PrefsDataSourceI
import com.github.andresvasquez.event_repository.data.source.remote.RemoteDataSourceI
import com.github.andresvasquez.event_repository.data.source.remote.api.ApiStatus
import com.github.andresvasquez.event_repository.model.NextTripSearch
import com.github.andresvasquez.event_repository.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import com.github.andresvasquez.event_repository.data.Result
import com.github.andresvasquez.event_repository.data.toEventDto
import com.github.andresvasquez.event_repository.model.EventDetailDomain

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
class EventRepository(
    private val prefs: PrefsDataSourceI,
    private val local: LocalDataSourceI,
    private val remote: RemoteDataSourceI
) : EventRepositoryI {

    private val _status = MutableLiveData<ApiStatus>()
    override val status: LiveData<ApiStatus>
        get() = _status


    override fun getNextTripPrefs(): NextTripSearch? {
        return prefs.getNextTripPrefs()
    }

    override fun saveNextTripPrefs(nextTrip: NextTripSearch) {
        return prefs.saveNextTripPrefs(nextTrip)
    }

    override suspend fun refreshData() {
        if (_status.value != ApiStatus.LOADING) {
            withContext(Dispatchers.IO) {
                try {
                    updateStatus(ApiStatus.LOADING)
                    val nextTripSearch = prefs.getNextTripPrefs()
                    val results = nextTripSearch?.let {
                        remote.getEvents(
                            city = it.city,
                            startDateTime = nextTripSearch.startDateTime,
                            endDateTime = nextTripSearch.endDateTime,
                            genreId = nextTripSearch.genreId,
                            sort = Constants.API_SORT,
                            size = Constants.API_PAGE_SIZE,
                            page = Constants.STARTING_PAGE_INDEX
                        )
                    }

                    if (results is Result.Success) {
                        val dbEvents = results.data.toEventDto()
                        local.deleteEvents()
                        local.insertEvents(dbEvents.toList())
                    } else {
                        if (results is Result.Error) {
                            Timber.e(results.exception)
                        } else {
                            Timber.e(results.toString())
                        }
                        updateStatus(ApiStatus.ERROR)
                    }
                    updateStatus(ApiStatus.DONE)
                } catch (e: Exception) {
                    updateStatus(ApiStatus.ERROR)
                }
            }
        }
    }

    override fun getPagingEvents(): Flow<PagingData<EventDTO>> {
        val pagingSourceFactory = { local.getEvents() }
        return Pager(
            config = PagingConfig(
                pageSize = Constants.API_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = EventRemoteMediator(
                prefs,
                local,
                remote
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getEventById(eventId: String): Result<EventDTO> {
        return local.getEventById(eventId)
    }

    private suspend fun updateStatus(status: ApiStatus) {
        withContext(Dispatchers.Main) {
            _status.value = status
        }
    }
}