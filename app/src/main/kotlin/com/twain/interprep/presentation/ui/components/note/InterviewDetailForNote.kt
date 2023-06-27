package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
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
        Box() {
            val date = DateUtils.convertDateStringToDate(interview.date)
            DateTimeBox(borderColor = BackgroundDarkPurple, date = date)
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = interview.company)
            Text(text = "#${interview.roundNum} - ${interview.role}")
        }
    }
}

@Preview
@Composable
fun InterviewDetailForNotePreview() {
    InterviewDetailForNote(interview = interviewMockData)
}
