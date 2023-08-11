package com.twain.interprep.presentation.ui.components.generic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialColorPalette.onPrimary,
    iconColor: Color = MaterialColorPalette.onPrimary,
    backgroundColor: Color = MaterialColorPalette.primary,
    disabledContentColor: Color = MaterialColorPalette.onSurface,
    disabledContainerColor: Color = MaterialColorPalette.surfaceContainerHighest,
    enabled: Boolean = true,
    contentDescription: String? = null,
    textStyle: TextStyle,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    @DrawableRes leadingIcon: Int? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContentColor = disabledContentColor.copy(alpha = 0.3f),
            disabledContainerColor = disabledContainerColor.copy(alpha = 0.12f)
                .compositeOver(disabledContainerColor)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = dimensionResource(id = R.dimen.dimension_elevation_low)
        ),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        leadingIcon?.let {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = contentDescription,
                tint = if (enabled) iconColor else disabledContentColor.copy(alpha = 0.12f)
                    .compositeOver(disabledContainerColor)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text = text, style = textStyle)
    }
}

@Composable
fun IPOutlinedButton(
    backgroundColor: Color = Color.Transparent,
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    enabled: Boolean = true,
    contentDescription: String? = null,
    iconColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    @DrawableRes leadingIcon: Int? = null,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.dimension_stroke_width_low),
            color = borderColor,
        ),
        contentPadding = contentPadding
    ) {
        leadingIcon?.let {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = contentDescription,
                tint = iconColor
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text = text, style = textStyle)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun FilledButtonPreview() {
    InterPrepTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IPFilledButton(
                text = "View Notes",
                enabled = false,
                leadingIcon = R.drawable.ic_view_note_24,
                onClick = {},
                textStyle = MaterialTheme.typography.bodySmall,
            )
            IPFilledButton(
                backgroundColor = MaterialColorPalette.primaryContainer,
                textColor = MaterialColorPalette.onPrimaryContainer,
                iconColor = MaterialColorPalette.onPrimaryContainer,
                text = "Add Notes",
                enabled = true,
                leadingIcon = R.drawable.ic_add_note_24,
                onClick = {},
                textStyle = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun OutlinedButtonPreview() {
    InterPrepTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IPOutlinedButton(
                textColor = MaterialColorPalette.onPrimaryContainer,
                text = "Add Note",
                iconColor = MaterialColorPalette.onPrimaryContainer,
                leadingIcon = R.drawable.ic_outline_add_circle_24,
                onClick = {},
                borderColor = MaterialColorPalette.onPrimaryContainer,
                textStyle = MaterialTheme.typography.bodySmall
            )
            IPOutlinedButton(
                textColor = MaterialColorPalette.onPrimaryContainer,
                text = "Delete Note",
                iconColor = MaterialColorPalette.onPrimaryContainer,
                leadingIcon = R.drawable.ic_outline_delete_circle_24,
                onClick = {},
                borderColor = MaterialColorPalette.onPrimaryContainer,
                textStyle = MaterialTheme.typography.bodySmall
            )
        }
    }
}

