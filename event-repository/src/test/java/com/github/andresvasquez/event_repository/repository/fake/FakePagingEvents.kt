package com.github.andresvasquez.event_repository.repository.fake

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.utils.Constants

class FakePagingEvents(private val events: MutableList<EventDTO> = mutableListOf()) :
    PagingSource<Int, EventDTO>() {

    override fun getRefreshKey(state: PagingState<Int, EventDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventDTO> {
        return try {
            val pageNumber = params.key ?: 0
            val response = events.take(Constants.API_PAGE_SIZE)
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (events.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}