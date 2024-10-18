package com.example.cathaybkhomework.data

data class Panos (
    val total: Int,
    val data: List<PanosItem>
)

data class PanosItem(
    val id: Int,
    val title: String,
    val url: String,
    val modified: String,
)

data class Audio (
    val total: Int,
    val data: List<AudioItem>
)

data class AudioItem(
    val id: Int,
    val name: String,
    val url: String,
    val modified: String,
)