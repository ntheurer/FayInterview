package com.fayinterview.app.ui.webview

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.serialization.Serializable

@Serializable
data class WebViewScreen(val url: String)

@Composable
fun WebViewMain(
    url: String
) {
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
}
