package com.github.andresvasquez.event_repository.data.source.prefs

import com.github.andresvasquez.event_repository.model.NextTripSearch

interface PrefsDataSourceI {
    fun getNextTripPrefs(): NextTripSearch?
    fun saveNextTripPrefs(nextTrip: NextTripSearch)
}