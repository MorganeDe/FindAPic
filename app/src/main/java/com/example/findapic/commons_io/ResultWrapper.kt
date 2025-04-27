package com.example.findapic.commons_io

import com.example.findapic.domain.models.FindAPicError
import retrofit2.Response

suspend fun <T> wrapToResult(block: suspend () -> Response<T>): Result<T> {
    try {
        block().let {
            if (it.isSuccessful) {
                return it.body()?.let { body -> Result.success(body) } ?: Result.failure(
                    FindAPicError.UnknownError(NO_RESULT_BODY_FOUND_ERROR_MESSAGE)
                )
            } else {
                return when (it.code()) {
                    404 -> Result.failure(FindAPicError.ServerError(RESOURCE_NOT_FOUND_ERROR_MESSAGE))
                    500 -> Result.failure(FindAPicError.ServerError(SERVER_ERROR_MESSAGE))
                    else -> Result.failure(FindAPicError.UnknownError(UNKNOWN_ERROR_MESSAGE))
                }
            }
        }
    } catch (e: Exception) {
        return Result.failure(e.toFindAPicError())
    }
}

private const val NO_RESULT_BODY_FOUND_ERROR_MESSAGE = "No result body found"
private const val RESOURCE_NOT_FOUND_ERROR_MESSAGE = "Resource not found"
private const val SERVER_ERROR_MESSAGE = "Server error"
private const val UNKNOWN_ERROR_MESSAGE = "Something went wrong"