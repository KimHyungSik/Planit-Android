package com.ctu.core.util

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    class Success<T>(data: T, code: Int? = null) : Resource<T>(data, code = code)
    class Error<T>(message: String, data: T? = null, code: Int? = null) : Resource<T>(data, message, code)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class AppUpdate<T>() : Resource<T>(null)
}
