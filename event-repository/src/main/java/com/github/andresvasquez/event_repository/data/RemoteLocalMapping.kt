package com.github.andresvasquez.event_repository.data

import com.github.andresvasquez.event_repository.data.source.local.EventDTO
import com.github.andresvasquez.event_repository.data.source.remote.model.EventDetails
import java.util.*

//To save in the database
fun List<EventDetails>.toEventDto(): Array<EventDTO> {
    return map {
        EventDTO(
            id = it.id,
            name = it.name,
            type = it.type,
            url = it.url,
            locale = it.locale,
            adult = it.ageRestrictions.legalAgeEnforced,
            thumbnailImage = it.thumbnailImage(),
            coverImage = it.coverImage(),
            startDate = it.dates.start.dateTime,
            timezone = it.dates.timezone,
            info = it.info,
            covidInfo = it.pleaseNote,
            priceMax = it.priceRanges[0].max,
            priceMin = it.priceRanges[0].min,
            currency = it.priceRanges[0].currency,
            classifications = it.bulidClasifications(),
            locationName = it.embedded.venues[0].name,
            state = it.embedded.venues[0].state.name,
            city = it.embedded.venues[0].city.name,
            postalCode = it.embedded.venues[0].postalCode,
            address = it.embedded.venues[0].address.line1,
            latitude = it.embedded.venues[0].location.latitude,
            longitude = it.embedded.venues[0].location.longitude,
            lastSync = Calendar.getInstance().time
        )
    }.toTypedArray()
}


fun EventDetails.thumbnailImage(): String {
    return let {
        if (!it.images.isNullOrEmpty()) {
            val image = it.images.find { it.ratio == "4_3" }
            image!!.url
        } else {
            ""
        }
    }
}

fun EventDetails.coverImage(): String {
    return let {
        if (!it.images.isNullOrEmpty()) {
            val image = it.images.find { it.width > 1000 }
            image!!.url
        } else {
            ""
        }
    }
}

fun EventDetails.bulidClasifications(): List<String> {
    val list = mutableListOf<String>()
    let {
        it.classifications.forEach {
            if (it.segment != null) {
                list.add(it.segment.name)
            }
            if (it.genre != null) {
                list.add(it.genre.name)
            }
            if (it.subGenre != null) {
                list.add(it.subGenre.name)
            }
            if (it.type != null) {
                list.add(it.type.name)
            }
            if (it.subType != null) {
                list.add(it.subType.name)
            }
        }
    }

    return list
}