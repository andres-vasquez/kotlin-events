package com.github.andresvasquez.event_repository.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeys
import com.github.andresvasquez.event_repository.data.source.local.paging.RemoteKeysDao
import com.github.andresvasquez.event_repository.utils.DatabaseListTypeConverter
import com.github.andresvasquez.event_repository.utils.DatabaseTypeConverter

@Database(entities = [EventDTO::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseTypeConverter::class, DatabaseListTypeConverter::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventDAO
    abstract fun remoteKeysDao(): RemoteKeysDao
}

private lateinit var INSTANCE: EventDatabase
fun getDatabase(context: Context): EventDatabase {
    synchronized(EventDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EventDatabase::class.java,
                "events_db"
            ).build()
        }
    }
    return INSTANCE
}