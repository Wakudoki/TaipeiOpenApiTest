package com.example.cathaybkhomework.common.language

interface MultiLanguage {
    val latestNews: String
    val showMore: String
    val home: String
    val attraction: String
    val activityEvent: String
    val EventCalendar: String
    val tours: String
    val viewDetail: String
    val noImage: String
    val all: String
    val selectLanguage: String
    val selectCategories: String
    val businessHours: String
    val address: String
    val tel: String
    val url: String
    val attractionIntroduction: String
    val category: String
    val serviceFacilities: String
    val clearAll: String
}

object TraditionalChinese : MultiLanguage {
    override val latestNews: String = "最新消息"
    override val showMore: String = "顯示更多"
    override val home: String = "首頁"
    override val attraction: String = "遊憩景點"
    override val activityEvent: String = "活動展演"
    override val EventCalendar: String = "活動年曆"
    override val tours: String = "玩樂台北"
    override val viewDetail: String = "查看詳情"
    override val noImage: String = "無圖片顯示"
    override val all: String = "全部"
    override val selectLanguage: String = "選擇語言"
    override val selectCategories: String = "選擇類型"
    override val businessHours: String = "營業時間"
    override val address: String = "地址"
    override val tel: String = "連絡電話"
    override val url: String = "網址"
    override val attractionIntroduction: String = "景點介紹"
    override val category: String = "類型"
    override val serviceFacilities: String = "服務設施"
    override val clearAll: String = "清除全部"
}

object English : MultiLanguage {
    override val latestNews: String = "Latest News"
    override val showMore: String = "Show More"
    override val home: String = "Home"
    override val attraction: String = "Attraction"
    override val activityEvent: String = "Event"
    override val EventCalendar: String = "Event Calendar"
    override val tours: String = "Travel In Taipei"
    override val viewDetail: String = "View Details"
    override val noImage: String = "No Image"
    override val all: String = "All"
    override val selectLanguage: String = "Select language"
    override val selectCategories: String = "Select Categories"
    override val businessHours: String = "Business hours"
    override val address: String = "Address"
    override val tel: String = "Tel"
    override val url: String = "URL"
    override val attractionIntroduction: String = "Attraction Introduction"
    override val category: String = "Category"
    override val serviceFacilities: String = "Service Facilities"
    override val clearAll: String = "Clear All"
}