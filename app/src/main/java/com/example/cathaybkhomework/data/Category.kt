package com.example.cathaybkhomework.data

enum class CategoryType {
    Activity,// -展演活動
    Calendar,// -活動年曆
    Pictorial,// -台北畫刊
    Attractions,// -景點
    Gourmet,// -美食店家
    Consume,// -消費店家
    Accommodation,// -住宿
    Tours,// -遊程
}

data class Category(
    val total: Int,
    val data: List<CategoryItem>
)

data class CategoryItem(
    val Category: List<CategoryDetail>
)

data class CategoryDetail(
    val id: String,
    val name: String
)