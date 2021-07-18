package com.github.andresvasquez.kotlinevents.ui.splash

import android.app.Application
import com.github.andresvasquez.event_repository.data.source.EventRepositoryI
import com.udacity.nano.popularmovies.ui.base.BaseViewModel

class SplashViewModel(val app: Application, private val repository: EventRepositoryI) :
    BaseViewModel(app, repository) {
}