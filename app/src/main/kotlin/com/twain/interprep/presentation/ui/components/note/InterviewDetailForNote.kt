package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.presentation.ui.components.generic.DateTimeBox
import com.twain.interprep.presentation.ui.components.interview.interviewMockData
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.utils.DateUtils

@Composable
fun InterviewDetailForNote(
    modifier:Modifier = Modifier,
    interview: Interview
) {
    Row(modifier = modifier) {
        Box {
            val date = DateUtils.convertDateStringToDate(interview.date)
            DateTimeBox(borderColor = BackgroundDarkPurple, date = date)
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
        Box(modifier = Modifier.height(80.dp)) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = interview.company,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("#${interview.roundNum}")
                        }
                        append(" - ${interview.role}")
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.icon_more_vert_content_description)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun InterviewDetailForNotePreview() {
    InterviewDetailForNote(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_16dp), dimensionResource(id = R.dimen.dimension_12dp)), interview = interviewMockData)
}
