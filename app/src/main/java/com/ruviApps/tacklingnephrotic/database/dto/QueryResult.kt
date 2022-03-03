package com.ruviApps.tacklingnephrotic.database.dto

/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class QueryResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : QueryResult<T>()
    data class Error(val message: String?, val statusCode: Int? = null) :
        QueryResult<Nothing>()
}