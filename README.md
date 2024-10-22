# 台北市開放資料 (OpenData)

## 旅遊資訊API [`website`](https://www.travel.taipei/open-api/swagger/ui/index#/Tours/Tours_Theme)

## 首頁 HomeFragment
會顯示各三筆最新資訊、遊憩景點的資料，在點擊顯示更多後會多加三筆資料  
Topbar支援顯示暗黑模式切換、語言切換等功能

<img src="/screenshot/home_tw.jpg" width="300" />

## 遊憩景點 AttractionsFragment
會根據選取的類型顯示景點列表，  
點擊過濾圖示會開始類型視窗，  
未選擇類型時會顯示全部的景點。
  
點擊景點會開啟詳細資訊頁面，  
圖片以Pager形式呈現，可以左右滑動以查看內容

<img src="/screenshot/attractions.jpg" width="300" /> <img src="/screenshot/attractions_detail.jpg" width="300" />

## 活動展演 ActivityEventFragment
網址會以WebView形式打開  
而相關連結則會以Browser形式開啟  

<img src="/screenshot/activity_event.jpg" width="300" /> <img src="/screenshot/activity_event_detail.jpg" width="300" />

## 活動年曆 EventCalendarFragment
會依照日期排序，  
並以月為基準分為群組  
<img src="/screenshot/event_calendar.jpg" width="300" />

## 主題旅遊 ToursFragmnent
目前API的請求偶爾會失敗
需多次刷新嘗試  
<img src="/screenshot/tours.jpg" width="300" />

## 其他
多語言 MyLanguage
DataStore MyDataStore
