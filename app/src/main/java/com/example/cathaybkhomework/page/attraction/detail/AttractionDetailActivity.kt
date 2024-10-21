package com.example.cathaybkhomework.page.attraction.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.cathaybkhomework.R
import com.example.cathaybkhomework.common.composable.LocalColorBackgroundOriginal
import com.example.cathaybkhomework.common.composable.LocalColorBluePrimary
import com.example.cathaybkhomework.common.composable.LocalColorBlueSecondary
import com.example.cathaybkhomework.common.composable.LocalColorLine2
import com.example.cathaybkhomework.common.composable.LocalColorTextTitle
import com.example.cathaybkhomework.common.composable.LocalLanguageOf
import com.example.cathaybkhomework.common.composable.LocalThemeModeOf
import com.example.cathaybkhomework.common.language.MyLanguage
import com.example.cathaybkhomework.data.AttractionItem
import com.example.cathaybkhomework.page.wabview.WebViewActivity
import com.example.cathaybkhomework.ui.theme.ThemeMode
import com.example.myandroid.common.language.MyModel
import com.example.myandroid.extension.clickableNoRipple
import com.google.gson.Gson

class AttractionDetailActivity : ComponentActivity() {

    companion object {
        fun newIntent(
            context: Context,
            title: String,
            attractionItem: AttractionItem
        ): Intent {
            return Intent(context, AttractionDetailActivity::class.java).apply {
                putExtra("title", title)
                putExtra("attractionItem", Gson().toJson(attractionItem))
            }
        }
    }

    private val title by lazy {
        intent.getStringExtra("title") ?: MyLanguage.strings.attraction
    }

    private val attractionItem by lazy {
        Gson().fromJson(intent.getStringExtra("attractionItem"), AttractionItem::class.java)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalThemeModeOf provides ThemeMode[MyModel.themeMode],
                LocalLanguageOf provides MyLanguage[MyModel.languageKey]
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        onBackPressed()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            title = {
                                Text(text = title)
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                titleContentColor = LocalColorTextTitle,
                                containerColor = LocalColorBackgroundOriginal
                            )
                        )
                    },
                ) { paddingValue ->
                    Column(
                        modifier = Modifier
                            .background(LocalColorBackgroundOriginal)
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValue),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AttractionImages()

                        AttractionInfo()

                        AttractionIntroduction()

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    private fun AttractionImages() {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val pagerState = rememberPagerState(pageCount = { attractionItem.images.size })
        Box {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState
            ) { page ->
                Box(
                    modifier = Modifier
                        .width(screenWidth)
                        .height(screenWidth / 1.2f)
                ) {
                    GlideImage(
                        model = attractionItem.images[page].src,
                        contentDescription = "Attraction Image",
                        contentScale = ContentScale.Crop,
//                    loading = placeholder(R.drawable.ic_image_placehold), //這邊Glide似乎有bug，設定loading placeholder後未滑動前圖片出不來
                        failure = placeholder(R.drawable.ic_image_error_placehold),
                    )
                }
            }

            //indicator
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.White else Color.White.copy(
                            alpha = 0.5f
                        )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun AttractionInfo() {
        val column1Weight = .25f
        val column2Weight = .75f
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //營業時間
            if (attractionItem.open_time.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(column1Weight),
                        text = MyLanguage.strings.businessHours + "：",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )

                    Text(
                        modifier = Modifier.weight(column2Weight),
                        text = attractionItem.open_time,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )
                }
            }

            //地址
            if (attractionItem.address.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(column1Weight),
                        text = MyLanguage.strings.address + "：",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )

                    Text(
                        modifier = Modifier.weight(column2Weight),
                        text = attractionItem.address,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )
                }
            }

            //連絡電話
            if (attractionItem.tel.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(column1Weight),
                        text = MyLanguage.strings.tel + "：",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )

                    Text(
                        modifier = Modifier.weight(column2Weight),
                        text = attractionItem.tel,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )
                }
            }

            //網址
            if (attractionItem.url.isNotEmpty()) {
                Row(
                    modifier = Modifier.clickableNoRipple {
                        startActivity(
                            WebViewActivity.newIntent(
                                context = this@AttractionDetailActivity,
                                url = attractionItem.url,
                                title = attractionItem.name
                            )
                        )
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(column1Weight),
                        text = MyLanguage.strings.url + "：",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )

                    Text(
                        modifier = Modifier.weight(column2Weight),
                        text = attractionItem.url,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = 12.sp,
                            color = LocalColorBlueSecondary
                        ),
                    )
                }
            }

            //分類
            if (attractionItem.category.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(column1Weight),
                        text = MyLanguage.strings.category + "：",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )

                    Row(
                        modifier = Modifier.weight(column2Weight),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(attractionItem.category.size) {
                            Text(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(LocalColorLine2)
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                text = attractionItem.category[it].name,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = LocalColorTextTitle
                                )
                            )
                        }
                    }
                }
            }

            //服務
            if (attractionItem.service.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(column1Weight),
                        text = MyLanguage.strings.serviceFacilities + "：",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LocalColorTextTitle
                        ),
                    )

                    Row(
                        modifier = Modifier.weight(column2Weight),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(attractionItem.service.size) {
                            Row {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(R.drawable.ic_check),
                                    contentDescription = "",
                                    tint = LocalColorBluePrimary
                                )

                                Text(
                                    text = attractionItem.service[it].name,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = LocalColorTextTitle
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun AttractionIntroduction() {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp),
            text = MyLanguage.strings.attractionIntroduction,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = LocalColorTextTitle
            ),
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = attractionItem.introduction,
            style = TextStyle(
                fontSize = 14.sp,
                color = LocalColorTextTitle
            ),
        )
    }
}