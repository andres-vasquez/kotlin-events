package com.github.andresvasquez.event_repository.utils

import com.github.andresvasquez.event_repository.model.NextTripSearch

object Constants {
    const val BASE_URL = "https://app.ticketmaster.com"
    const val API_SORT = "date,asc"
    const val API_PAGE_SIZE = 20

    //PAGING
    const val STARTING_PAGE_INDEX = 1


    //PREFS
    const val SHARED_PREFS_KEY = "eventsPrefs"
    const val PREFS_NEXT_TRIP = "nextTripPreference"

    //Error codes
    //1000 family, server
    const val UNKNOWN_ERROR = 1000
    const val NO_EVENTS_ERROR = 1001
    const val SERVER_ERROR = 1002
    const val REQUEST_ERROR = 1003

    //2000 family, local db
    const val EVENT_NOT_FOUND = 2000

    //Default values
    val DEFAULT_NEXT_TRIP: NextTripSearch = NextTripSearch(
        city = "New York",
        startDateTime = "2021-09-25T00:00:00Z",
        endDateTime = "2021-10-01T00:00:00Z",
        genreId = "KnvZfZ7v7l1",
        sort = API_SORT
    )
}