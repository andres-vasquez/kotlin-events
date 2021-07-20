package com.github.andresvasquez.event_repository.data.source.local.paging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val eventId: String,
    val prevKey: Int?,
    val nextKey: Int?
)