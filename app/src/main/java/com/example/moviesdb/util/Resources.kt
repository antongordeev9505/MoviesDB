package com.example.moviesdb.util

//typealias SimpleResource = Resource<Unit>

//sealed class Resource<T>(val data: T? = null, val message: String? = null) {
//    class Loading<T>(data: T? = null): Resource<T>(data)
//    class Success<T>(data: T?): Resource<T>(data)
//    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
//}

//sealed class Resource<out T: Any> {
//    data class Success<out T: Any>(val data: T): Resource<T>()
//    data class Error(val exception: String): Resource<Nothing>()
//    data class Loading() : Resource<Nothing>()
//}

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Error(val exception: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}