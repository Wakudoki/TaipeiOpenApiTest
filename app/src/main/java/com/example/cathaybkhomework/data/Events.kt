package com.example.cathaybkhomework.data

data class News (
    val total: Int,
    val data: List<NewsItem>
)

data class NewsItem(
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val posted: String,
    val modified: String,
    val links: List<Link>
)

data class Activity(
    val total: Int,
    val data: List<ActivityItem>
)

data class ActivityItem(
    val id: Int,
    val contact: String,
    val tel: String,
    val title: Int,
    val description: String,
    val begin: String,
    val end: String,
    val posted: String,
    val modified: String,
    val url: String,
    val links: List<Link>
)

data class Link(
    val src: String,
    val subject: String
)

data class Calendar(
    val total: Int,
    val data: List<CalendarItem>
)

data class CalendarItem(
    val title: Int,
    val description: String,
    val begin: String,
    val end: String,
    val posted: String,
    val modified: String,
    val url: String
)