package com.github.andresvasquez.event_repository.exceptions

import com.github.andresvasquez.event_repository.utils.Constants

class EventNotFoundException(message: String) : EventException(message, Constants.EVENT_NOT_FOUND) {
}