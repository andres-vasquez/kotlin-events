package com.github.andresvasquez.kotlinevents.utils

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun parseDateToString(dateAsString: String?, format: String = Constants.DATE_FORMAT): String {
    return try {
        return if (dateAsString != null) {
            val sourceSdf = SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT, Locale.getDefault())
            val requiredSdf = SimpleDateFormat(format, Locale.getDefault())
            requiredSdf.format(sourceSdf.parse(dateAsString))
        } else {
            ""
        }
    } catch (ex: Exception) {
        Timber.e(ex)
        ""
    }
}

fun dateToString(date: Date?, format: String = Constants.DATE_FORMAT): String {
    return if (date != null) {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.format(date)
    } else {
        ""
    }
}