package com.github.andresvasquez.event_repository.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class DatabaseTypeConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromLong(long: Long): Date {
        return Date(long)
    }
}

class DatabaseListTypeConverter {
    @TypeConverter
    fun fromList(data: List<String>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return Gson().fromJson(data, listType)
    }
}