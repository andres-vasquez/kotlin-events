package com.github.andresvasquez.event_repository.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventDTO(
    @ColumnInfo(name = "id") @PrimaryKey var id: String,
)