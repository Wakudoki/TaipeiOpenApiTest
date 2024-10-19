package com.example.cathaybkhomework.common.language

interface MultiLanguage {
    val latestNews: String
    val showMore: String
    val home: String
    val attraction: String
}

object TraditionalChinese : MultiLanguage {
    override val latestNews: String = "最新消息"
    override val showMore: String = "顯示更多"
    override val home: String = "首頁"
    override val attraction: String = "遊憩景點"
}

object English : MultiLanguage {
    override val latestNews: String = "Latest News"
    override val showMore: String = "Show More"
    override val home: String = "Home"
    override val attraction: String = "Attraction"
}