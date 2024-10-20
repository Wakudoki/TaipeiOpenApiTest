package com.example.cathaybkhomework.common.language

interface MultiLanguage {
    val latestNews: String
    val showMore: String
    val home: String
    val attraction: String
    val viewDetail: String
    val noImage: String
    val all: String
    val selectLanguage: String
    val selectCategories: String
}

object TraditionalChinese : MultiLanguage {
    override val latestNews: String = "最新消息"
    override val showMore: String = "顯示更多"
    override val home: String = "首頁"
    override val attraction: String = "遊憩景點"
    override val viewDetail: String = "查看詳情"
    override val noImage: String = "無圖片顯示"
    override val all: String = "全部"
    override val selectLanguage: String = "選擇語言"
    override val selectCategories: String = "選擇類型"
}

object English : MultiLanguage {
    override val latestNews: String = "Latest News"
    override val showMore: String = "Show More"
    override val home: String = "Home"
    override val attraction: String = "Attraction"
    override val viewDetail: String = "View Details"
    override val noImage: String = "No Image"
    override val all: String = "All"
    override val selectLanguage: String = "Select language"
    override val selectCategories: String = "Select Categories"
}