package com.example.cathaybkhomework.data

data class Event (
    val total: Int,
    val data: List<EventItem>
)

data class EventItem(
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val posted: String,
    val modified: String,
)