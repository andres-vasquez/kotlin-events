package com.github.andresvasquez.kotlinevents.di

import android.app.Application
import android.content.Context
import com.github.andresvasquez.event_repository.EventFacade
import com.github.andresvasquez.event_repository.data.source.EventRepository
import com.github.andresvasquez.event_repository.data.source.EventRepositoryI
import com.github.andresvasquez.event_repository.data.source.local.LocalDataSource
import com.github.andresvasquez.event_repository.data.source.local.LocalDataSourceI
import com.github.andresvasquez.event_repository.data.source.local.getDatabase
import com.github.andresvasquez.event_repository.data.source.prefs.PrefsDataSource
import com.github.andresvasquez.event_repository.data.source.prefs.PrefsDataSourceI
import com.github.andresvasquez.event_repository.data.source.remote.RemoteDataSource
import com.github.andresvasquez.event_repository.data.source.remote.RemoteDataSourceI
import com.github.andresvasquez.kotlinevents.BuildConfig
import com.github.andresvasquez.kotlinevents.ui.detail.EventDetailViewModel
import com.github.andresvasquez.kotlinevents.ui.list.EventListViewModel
import com.github.andresvasquez.kotlinevents.ui.splash.SplashViewModel
import com.github.andresvasquez.kotlinevents.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class EventsDi {
    companion object {
        fun buildDI(app: Application) {
            val myModule = module {
                //View Model
                viewModel { SplashViewModel(get(), get()) }
                viewModel { EventListViewModel(get(), get()) }
                viewModel { EventDetailViewModel(get(), get()) }

                //Repository components
                single {
                    EventFacade(get())
                }
                single {
                    EventRepository(get(), get(), get()) as EventRepositoryI
                }
                single {
                    LocalDataSource(get()) as LocalDataSourceI
                }
                single {
                    RemoteDataSource(Constants.API_KEY) as RemoteDataSourceI
                }
                single {
                    PrefsDataSource(app) as PrefsDataSourceI
                }
                single {
                    getDatabase(app)
                }
            }

            startKoin {
                androidContext(app.applicationContext)
                modules(listOf(myModule))
            }
        }
    }
}