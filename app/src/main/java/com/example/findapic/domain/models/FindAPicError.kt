package com.example.findapic.domain.models

sealed class FindAPicError(override val message: String) : Throwable() {
    class ServerError(message: String) : FindAPicError(message)
    class NetworkError(message: String) : FindAPicError(message)
    class IOError(message: String) : FindAPicError(message)
    class UnknownError(message: String) : FindAPicError(message)
}