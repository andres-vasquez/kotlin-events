package com.github.andresvasquez.event_repository.model

import java.util.*

data class EventListDomain(
    val id: String,
    val name: String,
    val thumbnailImage: String,
    val startDate: Date,
    val priceMax: Double?,
    val priceMin: Double?,
    val currency: String?,
    val city: String?,
    val classifications: List<String>,
)

data class EventDetailDomain(
    var id: String,
    val name: String,
    val type: String,
    val url: String,
    val locale: String,
    val adult: Boolean,
    val thumbnailImage: String,
    val coverImage: String,
    val startDate: Date,
    val timezone: String,
    val info: String,
    val covidInfo: String,
    val priceMax: Double?,
    val priceMin: Double?,
    val currency: String?,
    val classifications: List<String>,

    //Extra info
    val locationName: String?,
    val state: String?,
    val city: String?,
    val postalCode: Int?,
    val address: String?,
    val longitude: Double?,
    val latitude: Double?,
)