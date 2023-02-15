package com.udacity.project4.locationreminders.data.dto


/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val message: String?, val statusCode: Int? = null) :
        Result<Nothing>()
}
val Result<*>.succeeded
    get() = this is Result.Success && data != null
val Result<*>.error
    get() = this is Result.Error && message != null