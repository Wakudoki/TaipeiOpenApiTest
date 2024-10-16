package com.example.cathaybkhomework.retrofit

sealed class NetworkResult<T>(
    val status: ApiStatus,
    val data: T? = null,
    val message: String? = null
) {
    data class Success<T>(val _data: T) : NetworkResult<T>(status = ApiStatus.Success, data = _data, message = null)

    data class Fail<T>(val _message: String) : NetworkResult<T>(status = ApiStatus.Fail, message = _message)

    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>(status = ApiStatus.Loading)
}

enum class ApiStatus {
    Loading,
    Success,
    Fail
}