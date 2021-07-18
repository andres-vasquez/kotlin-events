package com.github.andresvasquez.event_repository.exceptions

import com.github.andresvasquez.event_repository.utils.Constants

class RequestErrorException(message: String) : EventException(message, Constants.REQUEST_ERROR) {
}