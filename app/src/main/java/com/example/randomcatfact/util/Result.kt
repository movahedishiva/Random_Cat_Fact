package com.example.randomcatfact.util

sealed class Result<out R> {
    data class Success<out R>(val data: R) : Result<R>()
    data class Error(val exception: Exception) : Result<Nothing>()

    /**
     * Get the result data if it is Success else null.
     */
    fun data(): R? {
        return when (this) {
            is Success -> data
            else -> null
        }
    }
}