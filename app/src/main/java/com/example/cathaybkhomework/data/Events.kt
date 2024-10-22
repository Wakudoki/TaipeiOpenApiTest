package com.example.cathaybkhomework.data

data class News(
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

data class ActivityEvent(
    val total: Int,
    val data: List<ActivityEventItem>
)

data class ActivityEventItem(
    val id: Int,
    val contact: String,
    val tel: String,
    val title: String,
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

data class EventCalendar(
    val total: Int,
    val data: List<EventCalendarItem>
)

data class EventCalendarItem(
    val id: Int,
    val tel: String,
    val title: String,
    val is_major: Boolean,
    val description: String,
    val begin: String,
    val end: String,
    val posted: String,
    val modified: String,
    val url: String,
    val links: List<Link>
)