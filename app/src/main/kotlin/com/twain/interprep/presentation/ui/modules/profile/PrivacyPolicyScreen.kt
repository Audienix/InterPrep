package com.twain.interprep.presentation.ui.modules.profile

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.twain.interprep.constants.StringConstants

@Composable
fun PrivacyPolicyScreen() {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
        }
    }, update = { webView -> webView.loadUrl(StringConstants.PRIVACY_POLICY_WEBSITE) })
}
