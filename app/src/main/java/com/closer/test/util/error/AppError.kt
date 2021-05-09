package com.closer.test.util.error

sealed class AppError(
    val error: Throwable?,
    private val message: String?,
    val imageResource: Int? = null) {

    class Connection(error: Throwable): AppError(error, null)

    class Database(error: Throwable): AppError(error, null)

    class NoResult(error: Throwable?, message: String?): AppError(error, message) {
        constructor(error: Throwable): this(error, null)
        constructor(message: String): this(null, message)
    }

    class Generic(error: Throwable): AppError(error, null)

    fun getMessage(): String {
        if (!message.isNullOrBlank()) return message

        return error?.message ?: "Failed to handle error message"
    }
}