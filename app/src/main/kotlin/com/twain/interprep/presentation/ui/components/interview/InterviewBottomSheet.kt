package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R
import com.twain.interprep.data.model.InterviewStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    bottomSheetState: SheetState,
    onNewStatusSelected: (InterviewStatus) -> Unit,
    highlightedStatus: InterviewStatus
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.dimension_16dp),
                    end = dimensionResource(id = R.dimen.dimension_16dp),
                    top = dimensionResource(id = R.dimen.dimension_4dp),
                    bottom = dimensionResource(id = R.dimen.dimension_24dp)
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Interview Status", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.dimension_24dp)
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IPInterviewStatus(
                    status = InterviewStatus.NO_UPDATE,
                    shouldHighLight = highlightedStatus == InterviewStatus.NO_UPDATE
                ) {
                    onNewStatusSelected(it)
                }
                IPInterviewStatus(
                    status = InterviewStatus.NEXT_ROUND,
                    shouldHighLight = highlightedStatus == InterviewStatus.NEXT_ROUND
                ) {
                    onNewStatusSelected(it)

                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IPInterviewStatus(
                    status = InterviewStatus.REJECTED,
                    shouldHighLight = highlightedStatus == InterviewStatus.REJECTED
                ) {
                    onNewStatusSelected(it)

                }
                IPInterviewStatus(
                    status = InterviewStatus.SELECTED,
                    shouldHighLight = highlightedStatus == InterviewStatus.SELECTED
                ) {
                    onNewStatusSelected(it)
                }
            }
        }
    }
}
