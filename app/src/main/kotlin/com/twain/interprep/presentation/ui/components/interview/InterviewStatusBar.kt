package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twain.interprep.R
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.data.model.getBackgroundColor
import com.twain.interprep.data.model.getCircleColor
import com.twain.interprep.data.model.getText

@Composable
fun InterviewStatusBar(
    modifier: Modifier = Modifier,
    status: InterviewStatus,
    shouldHighLight: Boolean = false,
    onClick: (InterviewStatus) -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        elevation = elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        colors = CardDefaults.cardColors(containerColor = Color(status.getBackgroundColor())),
    ) {
        Row(
            modifier = (Modifier.border(
                BorderStroke(1.dp, Color(status.getCircleColor())),
                shape = RoundedCornerShape(40.dp)
            ).takeIf { shouldHighLight } ?: Modifier).then(
                modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color(status.getBackgroundColor()))
                    .width(120.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable { onClick(status) }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Circle(color = Color(status.getCircleColor()), size = 24.dp)
            Text(
                text = status.getText(),
                modifier = Modifier.padding(start = 4.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
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
    InterviewStatusBar(status = InterviewStatus.NEXT_ROUND, onClick = {})
}