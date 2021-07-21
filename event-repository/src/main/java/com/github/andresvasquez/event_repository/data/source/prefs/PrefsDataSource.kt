package com.github.andresvasquez.event_repository.data.source.prefs

import android.app.Application
import android.content.Context
import com.github.andresvasquez.event_repository.model.NextTripSearch
import com.github.andresvasquez.event_repository.utils.Constants
import com.google.gson.Gson

class PrefsDataSource(val app: Application) : PrefsDataSourceI {

    override fun getNextTripPrefs(): NextTripSearch? {
        val sharedPref = app.getSharedPreferences(Constants.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        val asString = sharedPref.getString(Constants.PREFS_NEXT_TRIP, null)
        return try {
            Gson().fromJson(asString, NextTripSearch::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun saveNextTripPrefs(nextTrip: NextTripSearch) {
        val sharedPref = app.getSharedPreferences(Constants.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(Constants.PREFS_NEXT_TRIP, Gson().toJson(nextTrip))
            apply()
        }
    }

}