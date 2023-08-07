package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun IPInterviewStatus(
    modifier: Modifier = Modifier,
    status: InterviewStatus,
    shouldHighLight: Boolean = false,
    onClick: (InterviewStatus) -> Unit
) {
    val borderStroke = if (shouldHighLight)
        BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            status.getSecondaryColor()
        ) else
        BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainerHighest
        )
    Card(
        modifier = modifier
            .widthIn(min = 100.dp)
            .border(
                borderStroke,
                shape = Shapes.large
            )
            .clickable { onClick(status) },
        shape = Shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainer),
    ) {

        Row(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.dimension_8dp),
                    end = dimensionResource(id = R.dimen.dimension_8dp),
                    top = dimensionResource(id = R.dimen.dimension_4dp),
                    bottom = dimensionResource(id = R.dimen.dimension_4dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
        ) {
            Circle(
                color = status.getSecondaryColor(),
                size = 20.dp
            )
            Text(
                text = stringResource(id = status.getResourceId()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialColorPalette.onSurface
            )
        }
    }
}

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    color: Color,
    size: Dp
) {
    Canvas(modifier = modifier.size(size), onDraw = {
        drawCircle(color = color)
    })
}

@Preview
@Composable
fun InterviewStatusPreview() {
    IPInterviewStatus(status = InterviewStatus.NEXT_ROUND, onClick = {})
}
