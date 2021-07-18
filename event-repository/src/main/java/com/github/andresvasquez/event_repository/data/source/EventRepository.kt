package com.github.andresvasquez.event_repository.data.source

import com.github.andresvasquez.event_repository.data.source.local.LocalDataSourceI
import com.github.andresvasquez.event_repository.data.source.prefs.PrefsDataSourceI
import com.github.andresvasquez.event_repository.data.source.remote.RemoteDataSourceI

class EventRepository(
    private val prefs: PrefsDataSourceI,
    private val local: LocalDataSourceI,
    private val remote: RemoteDataSourceI
) : EventRepositoryI {

}