package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.twain.interprep.R
import com.twain.interprep.data.ui.TextLabelData
import com.twain.interprep.presentation.ui.components.generic.IPText
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun InterviewDetailsCard(
    modifier: Modifier = Modifier,
    headerText: String,
    headerContentColor: Color,
    headerBackgroundColor: Color,
    labelList: List<TextLabelData>
) {
    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainerHigh
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_8dp)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dimension_16dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLow)
    ) {
        InterviewDetailsHeader(headerText, headerBackgroundColor, headerContentColor)
        InterviewDetailsList(labelList)
    }
}

@Composable
private fun InterviewDetailsList(labelList: List<TextLabelData>) {
    labelList.forEachIndexed { index, textLabelData ->
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                text = stringResource(id = textLabelData.labelTextId),
                color = MaterialColorPalette.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
            IPText(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.dimension_8dp),
                        vertical = dimensionResource(id = R.dimen.dimension_4dp)
                    ),
                text = textLabelData.labelValue,
                link = textLabelData.labelValue,
                textColor = MaterialColorPalette.onSurface,
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
        if (index < labelList.lastIndex)
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.dimension_16dp)
                    ),
                thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
                color = MaterialColorPalette.outlineVariant
            )
    }
}

@Composable
private fun InterviewDetailsHeader(
    headerText: String,
    headerBackgroundColor: Color,
    headerContentColor: Color
) {
    Box(
        modifier = Modifier
            .background(headerBackgroundColor),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(dimensionResource(id = R.dimen.dimension_8dp)),
            text = headerText,
            color = headerContentColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
