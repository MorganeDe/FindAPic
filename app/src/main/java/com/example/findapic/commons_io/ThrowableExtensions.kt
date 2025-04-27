package com.example.findapic.commons_io

import com.example.findapic.domain.models.FindAPicError
import java.io.IOException
import java.net.UnknownHostException

fun Throwable.toFindAPicError(): FindAPicError = when (this) {
    is FindAPicError -> this
    is UnknownHostException -> FindAPicError.NetworkError(message ?: "Unknown host")
    is IOException -> FindAPicError.IOError(message ?: "IO error")
    else -> FindAPicError.UnknownError(message ?: "Something went wrong")
}