package com.example.cathaybkhomework.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface LoadingState {
    val loadingState: StateFlow<Boolean>
    val refreshingState: StateFlow<Boolean>

    fun loading()
    fun noLoading()
    fun refreshing()
    fun noRefreshing()
}

class LoadingStateImpl : LoadingState {
    private val _loadingState = MutableStateFlow(false)
    override val loadingState = _loadingState.asStateFlow()

    private val _refreshingState = MutableStateFlow(false)
    override val refreshingState = _refreshingState.asStateFlow()

    override fun loading() {
        _loadingState.value = true
    }

    override fun noLoading() {
        _loadingState.value = false
    }

    override fun refreshing() {
        _refreshingState.value = true
    }

    override fun noRefreshing() {
        _refreshingState.value = false
    }
}