package com.twain.interprep.data.model

sealed class ViewResult<out T> {

    object UnInitialized : ViewResult<Nothing>()
    data class Loaded<T>(val data: T) : ViewResult<T>()
}
