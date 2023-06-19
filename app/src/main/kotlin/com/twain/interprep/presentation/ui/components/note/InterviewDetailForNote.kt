package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.model.Interview
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun InterviewDetailForNote(
    interview: Interview
){
    Row() {
        Box() {
            val date = DateUtils.convertDateStringToDate(interview.date)
            Box(
                //TODO change constant size value
                modifier = Modifier
                    .size(80.dp)
                    .clip(Shapes.medium)
                    .border(width = 1.dp, color = Color.Red, MaterialTheme.shapes.medium),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = SimpleDateFormat(
                            StringConstants.DT_FORMAT_DATE,
                            Locale.getDefault()
                        ).format(date),
                        color = Color.Red,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = SimpleDateFormat(
                            StringConstants.DT_FORMAT_MONTH_YEAR,
                            Locale.getDefault()
                        ).format(date).uppercase()
                            .replace(".", ""),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Column {
            Text(text = interview.company)
            Text(text = "#${interview.roundNum} - ${interview.role}")
        }
    }
}