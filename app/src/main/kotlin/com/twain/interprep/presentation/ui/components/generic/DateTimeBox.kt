package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.presentation.ui.theme.TextPrimary
import com.twain.interprep.presentation.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateTimeBox(
    modifier: Modifier = Modifier,
    bkgColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    dateTextColor:Color = TextPrimary,
    monthYearTextColor:Color = TextSecondary,
    date: Date
) {
    Box(
        //TODO change constant size value
        modifier = modifier
            .size(80.dp)
            .clip(Shapes.medium)
            .background(bkgColor)
            .border(width = borderWidth, color = borderColor, shape = Shapes.medium),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = SimpleDateFormat(
                    StringConstants.DT_FORMAT_DATE,
                    Locale.getDefault()
                ).format(date),
                color = dateTextColor,
                style = MaterialTheme.typography.displaySmall
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
