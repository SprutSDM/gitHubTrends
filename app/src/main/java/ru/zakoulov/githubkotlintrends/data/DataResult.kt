package ru.zakoulov.githubkotlintrends.data

sealed class DataResult<T>(val data: T?) {
    class Success<T>(data: T) : DataResult<T>(data)
    class Loading<T>(data: T? = null) : DataResult<T>(data)
    class Fail<T>(val message: String, data: T? = null) : DataResult<T>(data)

    fun isSuccess() = this is Success
    fun isLoading() = this is Loading
    fun isFail() = this is Fail
}
