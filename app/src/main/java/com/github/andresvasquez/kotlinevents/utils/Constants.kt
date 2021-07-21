package com.github.andresvasquez.kotlinevents.utils

import com.github.andresvasquez.kotlinevents.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY

    const val DATE_FORMAT = "dd/MM/YYYY"
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    //Google APIS
    const val BASE_GMAP_URL = "https://maps.google.com/maps/api/staticmap?center="
    const val BASE_GMAP_API_KEY = BuildConfig.GMAP_API_KEY
}