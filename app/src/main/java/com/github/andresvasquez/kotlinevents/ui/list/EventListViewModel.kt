package com.github.andresvasquez.kotlinevents.ui.list

import android.app.Application
import com.github.andresvasquez.event_repository.data.source.EventRepositoryI
import com.udacity.nano.popularmovies.ui.base.BaseViewModel

class EventListViewModel(val app: Application, private val repository: EventRepositoryI) :
    BaseViewModel(app, repository) {
}