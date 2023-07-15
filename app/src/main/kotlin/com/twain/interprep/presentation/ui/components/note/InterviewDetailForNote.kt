package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.presentation.ui.components.generic.IPDateTimeBox
import com.twain.interprep.presentation.ui.components.generic.IPDropdown
import com.twain.interprep.presentation.ui.components.generic.IPDropdownItem
import com.twain.interprep.presentation.ui.components.interview.formatRoundNumAndInterviewType
import com.twain.interprep.presentation.ui.components.interview.interviewMockData
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.utils.DateUtils

@Composable
fun InterviewDetailForNote(
    modifier: Modifier = Modifier,
    interview: Interview,
    shouldShowDeleteButton: Boolean,
    notesEmpty: Boolean,
    onDeleteInterview: () -> Unit,
    onDeleteNotes: () -> Unit
) {
    Row(modifier = modifier) {
        Box {
            val date = DateUtils.convertDateStringToDate(interview.date)
            IPDateTimeBox(borderColor = BackgroundDarkPurple, date = date)
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_16dp)))
        InterviewDetails(
            interview = interview,
            companyTextColor = Color.Black,
            roundTypeTextColor = Color.Gray,
            height = 80.dp
        )
       if (shouldShowDeleteButton) {
            IPDropdown(items = GetInterviewDropdownMenuItems(notesEmpty,
                onDeleteInterviewClicked = onDeleteInterview,
                onDeleteNotesClicked = onDeleteNotes))
       }
    }
}

@Composable
fun InterviewDetails(
    height: Dp,
    interview: Interview,
    companyTextColor: Color,
    companyTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    roundTypeTextColor: Color,
    roundTypeTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Box(modifier = Modifier.height(height)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = interview.company,
                color = companyTextColor,
                style = companyTextStyle
            )
            Text(
                text = formatRoundNumAndInterviewType(interview).ifEmpty {
                    stringResource(
                        id = R.string.no_text_available
                    )
                },
                color = roundTypeTextColor,
                style = roundTypeTextStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}
@Composable
private fun GetInterviewDropdownMenuItems(
    notesEmpty: Boolean,
    onDeleteInterviewClicked: () -> Unit,
    onDeleteNotesClicked: () -> Unit
): List<IPDropdownItem> {
    val menuItems = mutableListOf<IPDropdownItem>()
    menuItems.add(IPDropdownItem(stringResource(id = R.string.menuitem_delete_interview), Icons.Default.Delete, onDeleteInterviewClicked))
    if (!notesEmpty)
        menuItems.add(IPDropdownItem(stringResource(id = R.string.menuitem_delete_notes), Icons.Default.Delete, onDeleteNotesClicked))
    return menuItems
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun InterviewDetailForNotePreview() {
    InterviewDetailForNote(
        modifier = Modifier.padding(
            dimensionResource(id = R.dimen.dimension_16dp),
            dimensionResource(id = R.dimen.dimension_12dp)
        ), interview = interviewMockData, true, false, {}) {}
}
