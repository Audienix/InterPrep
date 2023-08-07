package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPHeader(
    modifier: Modifier,
    text: String,
    textColor: Color = MaterialColorPalette.onSurfaceVariant,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    alignTextToCenter: Boolean = false,
) {
    if (alignTextToCenter) {
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            modifier = modifier,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            modifier = modifier,
            fontWeight = fontWeight
        )
    }
}

@Composable
@Preview
fun PreviewHeader() {
    IPHeader(
        text = "Coming Next",
        textColor = Black,
        textStyle = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(
            dimensionResource(id = R.dimen.dimension_16dp)
        ),
        fontWeight = FontWeight.Bold
    )
}
