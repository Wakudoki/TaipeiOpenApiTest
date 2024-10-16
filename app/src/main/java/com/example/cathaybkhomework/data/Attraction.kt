package com.example.cathaybkhomework.data

data class Attraction(
    val total: Int,
    val data: List<AttractionItem>
)

data class AttractionItem(
    val id: Int,
    val name: String,
    val introduction: String,
    val openStatus: Int,
    val url: String,
)