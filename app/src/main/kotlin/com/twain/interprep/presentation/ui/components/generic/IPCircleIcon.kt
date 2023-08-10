package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPCircleIcon(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    size: Dp = dimensionResource(id = R.dimen.dimension_icon_size_24),
    containerColor: Color = MaterialColorPalette.primaryContainer,
    borderColor: Color = MaterialColorPalette.onPrimaryContainer,
    textColor: Color = MaterialColorPalette.onPrimaryContainer
) {
    Card(
        modifier = modifier.size(size),
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            borderColor
        ),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = textColor
        )
    ) {
        Box(
            modifier = Modifier
                .background(color = containerColor, shape = CircleShape)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
fun IPCircleIconPreview() {
    InterPrepTheme {
        IPCircleIcon(
            text = "MM",
            containerColor = MaterialColorPalette.tertiary,
            borderColor = MaterialColorPalette.onTertiary,
            textColor = MaterialColorPalette.onTertiary,
        )
    }
}
