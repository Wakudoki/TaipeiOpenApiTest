package com.example.cathaybkhomework.data

data class Tours(
    val total: Int,
    val data: List<TourItem>
)

data class TourItem(
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val modified: String,
    val season: List<String>?,
    val months: List<String>?
)