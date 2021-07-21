package com.github.andresvasquez.event_repository.exceptions

import com.github.andresvasquez.event_repository.utils.Constants
import timber.log.Timber

open class EventException(message: String, code: Int? = Constants.UNKNOWN_ERROR) : Exception(message) {
    init {
        Timber.e(code.toString() + ":" + message)
    }
}