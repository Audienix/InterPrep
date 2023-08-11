package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.isPast
import com.twain.interprep.data.ui.InterviewFormData.getTextLabelList
import com.twain.interprep.data.ui.interviewMockData
import com.twain.interprep.presentation.ui.components.generic.IPText
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun IPInterviewDetailsCard(
    modifier: Modifier = Modifier,
    interview: Interview,
    headerContentColor: Color,
    headerBackgroundColor: Color,
    onEditClick: () -> Unit
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
        InterviewDetailsHeader(interview, onEditClick, headerBackgroundColor, headerContentColor)
        InterviewDetailsList(interview)
    }
}

@Composable
private fun InterviewDetailsList(interview: Interview) {
    val context = LocalContext.current
    val labelList = getTextLabelList(interview, context)
    labelList.forEachIndexed { index, textLabelData ->
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                text = stringResource(id = textLabelData.labelTextId),
                color = MaterialColorPalette.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium,
            )
            IPText(
                modifier = Modifier
                    .padding(
                        dimensionResource(id = R.dimen.dimension_8dp)
                    ),
                text = textLabelData.labelValue,
                link = textLabelData.labelValue,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
        if (index < labelList.lastIndex)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_4dp)),
                thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
                color = MaterialColorPalette.outlineVariant
            )
    }
}

@Composable
private fun InterviewDetailsHeader(
    interview: Interview,
    onEditClick: () -> Unit,
    headerBackgroundColor: Color,
    headerContentColor: Color
) {
    Box(
        modifier = Modifier
            .background(headerBackgroundColor),
    ) {
        val text = "".takeIf { interview.date.isEmpty() } ?: SimpleDateFormat(
            StringConstants.DT_FORMAT_DD_MMMM_YYYY,
            Locale.getDefault()
        ).format(DateUtils.convertDateStringToDate(interview.date))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(dimensionResource(id = R.dimen.dimension_8dp)),
            text = text,
            color = headerContentColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        if (!interview.isPast()) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = dimensionResource(id = R.dimen.dimension_8dp)),
                onClick = onEditClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = headerContentColor,
                    contentDescription = stringResource(id = R.string.appbar_title_edit_interview)
                )
            }
        }
    }
}

@Preview
@Composable
fun InterviewDetailsCardPreview() {
    IPInterviewDetailsCard(
        interview = interviewMockData,
        headerBackgroundColor = BackgroundDarkPurple,
        headerContentColor = BackgroundLightPurple,
        onEditClick = {})
}

