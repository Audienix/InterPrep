package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPCircleIcon(
    modifier: Modifier = Modifier,
    size: Dp = dimensionResource(id = R.dimen.dimension_icon_size_24),
    containerColor: Color = MaterialColorPalette.primaryContainer,
    borderColor: Color? = null,
    contentColor: Color = MaterialColorPalette.onPrimaryContainer,
    content: @Composable (BoxScope.() -> Unit)
) {
    Card(
        modifier = modifier.size(size),
        border = borderColor?.let {
            BorderStroke(
                dimensionResource(id = R.dimen.dimension_stroke_width_low),
                it
            )
        },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Box(
            modifier = Modifier
                .background(color = containerColor, shape = CircleShape)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}


@Composable
fun IPCircleTextIcon(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    size: Dp,
    containerColor: Color = MaterialColorPalette.primaryContainer,
    borderColor: Color? = null,
    textColor: Color = MaterialColorPalette.onPrimaryContainer,
) {
    IPCircleIcon(
        modifier = modifier,
        size = size,
        containerColor = containerColor,
        borderColor = borderColor,
        contentColor = textColor
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Composable
fun IPAvatar(
    modifier: Modifier = Modifier,
    size: Dp,
    containerColor: Color,
    image: ImageVector,
    ) {
    IPCircleIcon(
        modifier = modifier,
        size = size,
        containerColor = containerColor
    ) {
        IPIcon(imageVector = image, tint = MaterialColorPalette.onSurfaceVariant) {}
    }
}



//@Preview
//@Composable
//fun IPCircleIconPreview() {
//    InterPrepTheme {
//        IPCircleTextIcon(
//            text = "MM",
//            containerColor = MaterialColorPalette.tertiary,
//            borderColor = MaterialColorPalette.onTertiary,
//            textColor = MaterialColorPalette.onTertiary,
//        )
//    }
//}
