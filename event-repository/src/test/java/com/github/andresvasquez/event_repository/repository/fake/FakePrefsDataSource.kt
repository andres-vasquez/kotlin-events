package com.github.andresvasquez.event_repository.repository.fake

import com.github.andresvasquez.event_repository.data.source.prefs.PrefsDataSourceI
import com.github.andresvasquez.event_repository.model.NextTripSearch

class FakePrefsDataSource : PrefsDataSourceI {
    private var nextTripSearch: NextTripSearch? = null
    override fun getNextTripPrefs(): NextTripSearch? {
        return this.nextTripSearch
    }

    override fun saveNextTripPrefs(nextTrip: NextTripSearch) {
        this.nextTripSearch = nextTrip
    }
}