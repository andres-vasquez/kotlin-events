package com.github.andresvasquez.event_repository.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "event")
data class EventDTO(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "eventId") var eventId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "locale") val locale: String,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "thumbnailImage") val thumbnailImage: String,
    @ColumnInfo(name = "coverImage") val coverImage: String,
    @ColumnInfo(name = "startDate") val startDate: Date,
    @ColumnInfo(name = "timezone") val timezone: String,
    @ColumnInfo(name = "info") val info: String?,
    @ColumnInfo(name = "covidInfo") val covidInfo: String?,
    @ColumnInfo(name = "priceMax") val priceMax: Double?,
    @ColumnInfo(name = "priceMin") val priceMin: Double?,
    @ColumnInfo(name = "currency") val currency: String?,
    @ColumnInfo(name = "classifications") val classifications: List<String>,

    //Extra info
    @ColumnInfo(name = "locationName") val locationName: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "postalCode") val postalCode: Int?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "latitude") val latitude: Double?,

    //Audit
    @ColumnInfo(name = "lastSync")
    val lastSync: Date,
)