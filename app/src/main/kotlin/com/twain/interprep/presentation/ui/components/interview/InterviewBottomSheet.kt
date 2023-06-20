package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twain.interprep.data.model.InterviewStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewBottomSheet(
    onDismissRequest: () -> Unit,
    bottomSheetState: SheetState,
    onNewStatusSelected: (InterviewStatus) -> Unit,
    highlightedStatus: InterviewStatus
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Text(text = "Interview Status", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 20.dp, start = 48.dp, end = 48.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InterviewStatusBar(status = InterviewStatus.NO_UPDATE, shouldHighLight = highlightedStatus == InterviewStatus.NO_UPDATE) {
                    onNewStatusSelected(it)
                }
                InterviewStatusBar(status = InterviewStatus.NEXT_ROUND, shouldHighLight = highlightedStatus == InterviewStatus.NEXT_ROUND) {
                    onNewStatusSelected(it)

                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
            ) {
                InterviewStatusBar(status = InterviewStatus.REJECTED, shouldHighLight = highlightedStatus == InterviewStatus.REJECTED) {
                    onNewStatusSelected(it)

                }
                InterviewStatusBar(status = InterviewStatus.SELECTED, shouldHighLight = highlightedStatus == InterviewStatus.SELECTED) {
                    onNewStatusSelected(it)

                }
            }
        }
    }
}