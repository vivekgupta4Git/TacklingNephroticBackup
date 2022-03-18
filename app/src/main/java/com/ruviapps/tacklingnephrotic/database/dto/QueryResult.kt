package com.ruviapps.tacklingnephrotic.database.dto

/**
 * A sealed class that act as a wrapper above result of queries. It maps
 * successful outcome with a value of type [T] and a message
 * or a failure with message and statusCode having default value
 */
sealed class QueryResult<out T : Any> {
    data class Success<out T : Any>(val data: T,val message : String? = "") : QueryResult<T>()
    data class Error(val message: String?, val statusCode: Int? = QUERY_FAILURE_STATUS_CODE) : QueryResult<Nothing>()
    companion object{
        const val QUERY_FAILURE_STATUS_CODE = 401
    }
}
/**
 * extension functions on QueryResult
 * using reified and inline keyword in the method explanation:
 * To access the information about the type of class,
 * we use a keyword called reified in Kotlin.
 * In order to use the reified type, we need to use the inline function.
 * If a function is marked as inline, then wherever the function is called,
 * the compiler will paste the whole body of the function there.
 *
 *
 */

inline fun <reified T : Any>QueryResult<T>.onSuccess(callback : (data : T,message : String?) -> Unit){
    if(this is QueryResult.Success){
        callback(data,message)
    }
}

/**
 * extension function on QueryResult
 * using reified and inline keyword in the method explanation:
 * To access the information about the type of class,
 * we use a keyword called reified in Kotlin.
 * In order to use the reified type, we need to use the inline function.
 * If a function is marked as inline, then wherever the function is called,
 * the compiler will paste the whole body of the function there.
 *
 */

inline fun<reified T:Any>QueryResult<T>.onFailure(callback: (message: String?,statusCode: Int?) -> Unit){
    if(this is QueryResult.Error){
        callback(message,statusCode)
    }
}