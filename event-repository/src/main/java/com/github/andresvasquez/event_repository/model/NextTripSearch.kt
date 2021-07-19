package com.github.andresvasquez.event_repository.model

import java.io.Serializable

data class NextTripSearch(
    val city: String,
    val startDateTime: String,
    val endDateTime: String,
    val sort: String,
    val genreId: String
) : Serializable