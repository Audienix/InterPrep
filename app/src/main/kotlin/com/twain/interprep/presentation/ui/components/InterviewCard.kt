package com.twain.interprep.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.presentation.ui.theme.Shapes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.presentation.ui.theme.BrightGreen
import com.twain.interprep.presentation.ui.theme.DarkGray
import com.twain.interprep.presentation.ui.theme.DateTimeGray
import com.twain.interprep.presentation.ui.theme.Gray
import com.twain.interprep.presentation.ui.theme.Green
import com.twain.interprep.presentation.ui.theme.LightGray
import com.twain.interprep.presentation.ui.theme.LightGreen
import com.twain.interprep.presentation.ui.theme.LightPurple
import com.twain.interprep.presentation.ui.theme.Primary30
import java.text.SimpleDateFormat
import java.util.Calendar

val calendar: Calendar = Calendar.getInstance()
val interview = Interview(
    interviewId = 1,
    date = calendar,
    company = "Uber",
    interviewType = "In-person",
    role = "Software Engineer",
    roundNum = 2,
    jobPostLink = "https://www.linkedin.com/jobs/collections/recommended/?currentJobId=3512066424",
    companyLink = "https://www.uber.com/ca/en/ride/",
    interviewer = "John Smith",
    interviewStatus = InterviewStatus.NEXTROUND
)

@Composable
@Preview
fun UpcomingInterviewCard() {
    calendar.set(Calendar.MONTH, Calendar.AUGUST)
    calendar.set(Calendar.YEAR, 2022)
    InterviewCard(
        interview = interview,
        onClick = {},
        color = InterviewCardColor.UpcomingInterviewCardColor
    )
}

@Composable
@Preview
fun ComingNextInterviewCard() {
    calendar.set(Calendar.MONTH, Calendar.AUGUST)
    calendar.set(Calendar.YEAR, 2022)
    InterviewCard(
        interview = interview,
        onClick = {},
        color = InterviewCardColor.ComingNextInterviewColor
    )
}

@Composable
@Preview
fun PastInterviewCard() {
    calendar.set(Calendar.MONTH, Calendar.AUGUST)
    calendar.set(Calendar.YEAR, 2022)
    InterviewCard(
        interview = interview,
        onClick = {},
        color = InterviewCardColor.PastInterviewCardColor
    )
}

@Composable
fun InterviewCard(
    interview: Interview,
    onClick: () -> Unit,
    color: InterviewCardColor,
    height: Dp = 106.dp
) {
    val containerColor: Color
    val dateBoxColor: Color
    val textColor: Color
    when (color) {
        is InterviewCardColor.UpcomingInterviewCardColor -> {
            containerColor = LightPurple
            dateBoxColor = Primary30
            textColor = MaterialTheme.colorScheme.surfaceTint
        }
        is InterviewCardColor.ComingNextInterviewColor -> {
            containerColor = LightGreen
            dateBoxColor = Green
            textColor = BrightGreen
        }
        is InterviewCardColor.PastInterviewCardColor -> {
            containerColor = Gray
            dateBoxColor = DarkGray
            textColor = LightGray
        }
    }
    Card(
        shape = Shapes.large,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(height),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(Shapes.large)
                    .background(dateBoxColor),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = SimpleDateFormat("dd").format(interview.date.time),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = SimpleDateFormat("MMM, yyyy").format(interview.date.time).uppercase()
                            .replace(".", ""),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp) ,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = SimpleDateFormat("EEE, hh:mm").format(interview.date.time)
                        .replace(".", "") + " "
                        + SimpleDateFormat("a").format(interview.date.time).uppercase()
                        .replace(".", ""),
                    color = DateTimeGray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = interview.company,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "#${interview.roundNum.toString()} - ${interview.role}",
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

sealed class InterviewCardColor {
    object UpcomingInterviewCardColor : InterviewCardColor()
    object ComingNextInterviewColor : InterviewCardColor()
    object PastInterviewCardColor : InterviewCardColor()
}

@Entity(tableName = "interviews")
data class Interview(
    @PrimaryKey(autoGenerate = true) val interviewId: Int,
    val date: Calendar,
    val company: String,
    val interviewType: String?,
    val role: String?,
    val roundNum: Int?,
    val jobPostLink: String?,
    val companyLink: String?,
    val interviewer: String?,
    val interviewStatus: InterviewStatus
)
