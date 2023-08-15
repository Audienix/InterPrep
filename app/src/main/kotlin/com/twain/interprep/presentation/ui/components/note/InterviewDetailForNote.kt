package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.presentation.ui.components.dashboard.formatRoundNumAndInterviewType
import com.twain.interprep.presentation.ui.components.dashboard.interviewMockData
import com.twain.interprep.presentation.ui.components.generic.IPDateTimeBox
import com.twain.interprep.presentation.ui.components.generic.IPDropdown
import com.twain.interprep.presentation.ui.components.generic.IPDropdownItem
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.utils.DateUtils

@Composable
fun InterviewDetailForNote(
    modifier: Modifier = Modifier,
    interview: Interview,
    shouldShowMenuOption: Boolean,
    notesEmpty: Boolean,
    backgroundColor: Color = Color.Transparent,
    onDeleteInterview: () -> Unit,
    onDeleteNotes: () -> Unit
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(70.dp)
    ) {
        val date = DateUtils.convertDateStringToDate(interview.date)

        IPDateTimeBox(
            borderColor = MaterialColorPalette.outline,
            date = date
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
        InterviewDetails(
            interview = interview
        )
        if (shouldShowMenuOption) {
            IPDropdown(
                items = getInterviewDropdownMenuItems(
                    notesEmpty,
                    onDeleteInterviewClicked = onDeleteInterview,
                    onDeleteNotesClicked = onDeleteNotes
                )
            )
        }
    }
}

@Composable
fun InterviewDetails(
    interview: Interview,
) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = interview.company,
            color = MaterialColorPalette.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = formatRoundNumAndInterviewType(interview).ifEmpty {
                stringResource(
                    id = R.string.label_no_text_available
                )
            },
            color = MaterialColorPalette.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun getInterviewDropdownMenuItems(
    notesEmpty: Boolean,
    onDeleteInterviewClicked: () -> Unit,
    onDeleteNotesClicked: () -> Unit
): List<IPDropdownItem> {
    val menuItems = mutableListOf<IPDropdownItem>()
    menuItems.add(
        IPDropdownItem(
            stringResource(id = R.string.menuitem_delete_interview),
            Icons.Default.Delete,
            onDeleteInterviewClicked
        )
    )
    if (!notesEmpty)
        menuItems.add(
            IPDropdownItem(
                stringResource(id = R.string.menuitem_delete_notes),
                Icons.Default.Delete,
                onDeleteNotesClicked
            )
        )
    return menuItems
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun InterviewDetailForNotePreview() {
    InterviewDetailForNote(
        modifier = Modifier.padding(
            dimensionResource(id = R.dimen.dimension_16dp),
            dimensionResource(id = R.dimen.dimension_12dp)
        ), interview = interviewMockData, shouldShowMenuOption = true, notesEmpty = false, onDeleteInterview = {}) {}
}
