package com.twain.interprep.presentation.ui.components.generic

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun IPWebView(
    modifier: Modifier = Modifier,
    url: String
) {
    AndroidView(modifier = modifier, factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
        }
    }, update = { webView -> webView.loadUrl(url) })
}
