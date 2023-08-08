package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun IPDateTimeBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = MaterialColorPalette.outline,
    borderWidth: Dp = dimensionResource(id = R.dimen.dimension_stroke_width_low),
    dateTextColor: Color = MaterialColorPalette.onSurface,
    monthYearTextColor: Color = MaterialColorPalette.onSurfaceVariant,
    date: Date
) {
    Box(
        modifier = modifier
            .clip(Shapes.medium)
            .aspectRatio(1f)
            .background(backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = Shapes.medium),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimension_4dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = SimpleDateFormat(
                    StringConstants.DT_FORMAT_DATE,
                    Locale.getDefault()
                ).format(date),
                color = dateTextColor,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = SimpleDateFormat(
                    StringConstants.DT_FORMAT_MONTH_YEAR,
                    Locale.getDefault()
                ).format(date).uppercase()
                    .replace(".", ""),
                color = monthYearTextColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
