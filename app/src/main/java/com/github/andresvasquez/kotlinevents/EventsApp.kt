package com.github.andresvasquez.kotlinevents

import android.app.Application
import com.github.andresvasquez.kotlinevents.di.EventsDi
import timber.log.Timber

class EventsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initDependencyInjection()
        Timber.plant(Timber.DebugTree())
    }

    private fun initDependencyInjection() {
        EventsDi.buildDI(this)
    }
}