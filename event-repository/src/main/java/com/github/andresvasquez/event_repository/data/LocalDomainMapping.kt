package com.github.andresvasquez.event_repository.data

import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import com.github.andresvasquez.event_repository.model.EventDetailDomain
import com.github.andresvasquez.event_repository.model.EventListDomain
import java.util.*

//To save in the database
fun EventDTO.toEventListDomain(): EventListDomain {
    return let {
        EventListDomain(
            id = it.id,
            name = it.name,
            thumbnailImage = it.thumbnailImage,
            startDate = it.startDate,
            priceMax = it.priceMax,
            priceMin = it.priceMin,
            currency = it.currency,
            classifications = it.classifications,
            city = it.city
        )
    }
}

fun EventDTO.toEventDetailDomain(): EventDetailDomain {
    return let {
        EventDetailDomain(
            id = it.id,
            name = it.name,
            type = it.type,
            url = it.url,
            locale = it.locale,
            adult = it.adult,
            thumbnailImage = it.thumbnailImage,
            coverImage = it.coverImage,
            startDate = it.startDate,
            timezone = it.timezone,
            info = it.info ?: "",
            covidInfo = it.covidInfo ?: "",
            priceMax = it.priceMax,
            priceMin = it.priceMin,
            currency = it.currency,
            classifications = it.classifications,
            locationName = it.locationName,
            state = it.state,
            city = it.city,
            postalCode = it.postalCode,
            address = it.address,
            latitude = it.latitude,
            longitude = it.longitude,
        )
    }
}