package com.example.cathaybkhomework.data

data class Attraction(
    val total: Int,
    val data: List<AttractionItem>
)

data class AttractionItem(
    val id: Int,
    val name: String,
    val introduction: String,
    val open_status: Int,
    val open_time: String,
    val address: String,
    val url: String,
    val tel: String,
    val remind: String,
    val modified: String,
    val images: List<ImageItem>,
)

data class ImageItem(
    val src: String,
    val subject: String,
    val ext: String
)