package com.github.andresvasquez.event_repository.exceptions

import com.github.andresvasquez.event_repository.utils.Constants

class ServerErrorException(message: String) : EventException(message, Constants.SERVER_ERROR) {
}