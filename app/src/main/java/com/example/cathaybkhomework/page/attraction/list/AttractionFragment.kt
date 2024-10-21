package com.example.cathaybkhomework.page.attraction.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import com.example.cathaybkhomework.ui.theme.MyTheme
import org.koin.android.ext.android.inject

class AttractionFragment : Fragment() {
    private val viewModel: AttractionViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = content {
        MyTheme {
            AttractionScreen(viewModel)
        }
    }
}