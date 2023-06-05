package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.ui.InterviewFormData.Companion.getTextLabelList
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundSurface
import com.twain.interprep.presentation.ui.theme.Purple100
import com.twain.interprep.presentation.ui.theme.TextPrimary
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun IPInterviewDetailsCard(modifier: Modifier = Modifier, interview: Interview) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_4dp)),
        shape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.dimension_16dp),
            topEnd = dimensionResource(id = R.dimen.dimension_16dp)
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
    ) {
        InterviewDetailsHeader(interview)
        InterviewDetailsList(interview)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
    }
}

@Composable
private fun InterviewDetailsList(interview: Interview) {
    val labelList = getTextLabelList(interview)
    labelList.forEachIndexed { index, textLabelData ->
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.dimension_8dp),
                        top = dimensionResource(id = R.dimen.dimension_8dp),
                        bottom = dimensionResource(id = R.dimen.dimension_8dp)
                    )
                    .weight(1.2f),
                text = stringResource(id = textLabelData.labelTextId),
                color = TextPrimary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.dimension_8dp),
                        top = dimensionResource(id = R.dimen.dimension_8dp),
                        bottom = dimensionResource(id = R.dimen.dimension_8dp)
                    )
                    .weight(2f),
                text = textLabelData.labelValue,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        if (index < labelList.lastIndex)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_4dp)),
                thickness = 1.dp,
                color = BackgroundSurface
            )
    }
}

@Composable
private fun InterviewDetailsHeader(interview: Interview) {
    Box(
        modifier = Modifier
            .background(BackgroundDarkPurple),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.dimension_8dp)),
            text = SimpleDateFormat(
                StringConstants.DT_FORMAT_DD_MMMM_YYYY,
                Locale.getDefault()
            ).format(interview.date),
            color = Purple100,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = dimensionResource(id = R.dimen.dimension_8dp)),
            onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Edit,
                tint = Purple100,
                contentDescription = stringResource(id = R.string.appbar_title_edit_interview)
            )
        }
    }
}

@Preview
@Composable
fun InterviewDetailsCardPreview() {
    IPInterviewDetailsCard(interview = interview)
}

