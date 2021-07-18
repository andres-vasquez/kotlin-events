package com.github.andresvasquez.event_repository.exceptions

import com.github.andresvasquez.event_repository.utils.Constants

class NoEventsFoundException(message: String) : EventException(message, Constants.NO_EVENTS_ERROR) {
}