package com.twain.interprep.presentation.ui.components.generic

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.TextPrimary
import java.net.MalformedURLException
import java.net.URL

@Composable
fun IPText(
    modifier: Modifier = Modifier,
    text: String? = null,
    link: String? = null,
    textColor: Color = TextPrimary,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val context = LocalContext.current
    if ((link != null && isUrlValid(link))) {
        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                append(text ?: link)
            }
        }
        Text(
            color = textColor,
            text = annotatedString,
            style = textStyle,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    // Open the URL in a browser
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        startActivity(context, intent, null)
                    } catch (e: ActivityNotFoundException) {
                        // Handle the case where no browser app is installed
                        showToast(context, R.string.error_no_browser_app)
                    }
                }
        )
    } else {
        val displayText = when {
            text.isNullOrEmpty() && link.isNullOrEmpty() -> stringResource(id = R.string.no_text_available)
            text.isNullOrEmpty() -> link
            else -> text
        }
        Text(
            color = textColor,
            text = displayText.toString(),
            style = textStyle,
            modifier = modifier.fillMaxWidth()
        )
    }
}

private fun isUrlValid(urlString: String): Boolean {
    return try {
        val url = URL(urlString)
        url.protocol != null && url.host != null
    } catch (e: MalformedURLException) {
        false
    }
}

private fun showToast(context: Context, messageId: Int) {
    val message = context.getString(messageId)
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun IPTextPreview() {
    Column {
        IPText(text = "Open Link", link = "https://www.example.com")
        IPText(link = "https://www.example.com")
        IPText(text = "Regular Text")
    }
}
