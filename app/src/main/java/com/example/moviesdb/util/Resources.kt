package com.example.moviesdb.util

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Error(val exception: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}