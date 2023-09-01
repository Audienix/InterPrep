package com.twain.interprep.presentation.ui.components.generic

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.twain.interprep.constants.StringConstants


@Composable
fun IPChromeTab(
    context: Context,
    url: String,
    onNoBrowserFound: @Composable () -> Unit = {}
) {

    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)
    val customBuilder = builder.build()
    customBuilder.intent.setPackage(StringConstants.CHROME_PACKAGE_NAME)
    try {
        customBuilder.launchUrl(context, Uri.parse(url))
    } catch (e: ActivityNotFoundException) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            ContextCompat.startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            onNoBrowserFound()
        }
    }
}
