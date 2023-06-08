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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants.Companion.DT_FORMAT_DATE
import com.twain.interprep.constants.StringConstants.Companion.DT_FORMAT_DAY
import com.twain.interprep.constants.StringConstants.Companion.DT_FORMAT_MONTH_YEAR
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.presentation.ui.theme.BackgroundDarkGray
import com.twain.interprep.presentation.ui.theme.BackgroundDarkGreen
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightGray
import com.twain.interprep.presentation.ui.theme.BackgroundLightGreen
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.presentation.ui.theme.TextPrimary
import com.twain.interprep.presentation.ui.theme.TextSecondary
import com.twain.interprep.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun InterviewCard(
    interview: Interview,
    onClick: () -> Unit,
    color: InterviewCardColor
) {
    val containerColor: Color
    val dateBoxColor: Color
    val textColor: Color
    when (color) {
        is InterviewCardColor.UpcomingInterviewCardColor -> {
            containerColor = BackgroundLightPurple
            dateBoxColor = BackgroundDarkPurple
            textColor = BackgroundDarkPurple
        }

        is InterviewCardColor.ComingNextInterviewColor -> {
            containerColor = BackgroundLightGreen
            dateBoxColor = BackgroundDarkGreen
            textColor = BackgroundDarkGreen
        }

        is InterviewCardColor.PastInterviewCardColor -> {
            containerColor = BackgroundLightGray
            dateBoxColor = BackgroundDarkGray
            textColor = BackgroundDarkGray
        }
    }
    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_8dp))
            .clickable(onClick = onClick)
            .height(106.dp), //TODO change constant height value
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimension_16dp))
        ) {
            val date = DateUtils.convertStringToDate(interview.date)
            Box(
                //TODO change constant size value
                modifier = Modifier
                    .size(80.dp)
                    .clip(Shapes.medium)
                    .background(dateBoxColor),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = SimpleDateFormat(
                            DT_FORMAT_DATE,
                            Locale.getDefault()
                        ).format(date),
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = SimpleDateFormat(
                            DT_FORMAT_MONTH_YEAR,
                            Locale.getDefault()
                        ).format(date).uppercase()
                            .replace(".", ""),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = dimensionResource(id = R.dimen.dimension_16dp)),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = SimpleDateFormat(
                        DT_FORMAT_DAY,
                        Locale.getDefault()
                    ).format(date).uppercase() + buildString {
                        append(", ")
                    } + interview.time,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = interview.company,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
                var roundRoleText = "N/A"
                if(interview.roundNum.isNotEmpty() && interview.role.isNotEmpty())
                    roundRoleText = "#${interview.roundNum} - ${interview.role}"
                Text(
                    text = roundRoleText,
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
@Preview
fun UpcomingInterviewCard() {
    InterviewCard(
        interview = interviewMockData,
        onClick = {},
        color = InterviewCardColor.UpcomingInterviewCardColor
    )
}

val interviewMockData = Interview(
    interviewId = 1,
    date = "07/07/2023",
    time = "07:23",
    company = "Uber",
    interviewType = "In-person",
    role = "Software Engineer",
    roundNum = "2",
    jobPostLink = "https://www.linkedin.com/jobs/collections/recommended/?currentJobId=3512066424",
    companyLink = "https://www.uber.com/ca/en/ride/",
    interviewer = "John Smith",
    interviewStatus = InterviewStatus.NEXT_ROUND
)

@Composable
@Preview
fun ComingNextInterviewCard() {
    InterviewCard(
        interview = interviewMockData,
        onClick = {},
        color = InterviewCardColor.ComingNextInterviewColor
    )
}

@Composable
@Preview
fun PastInterviewCard() {
    InterviewCard(
        interview = interviewMockData,
        onClick = {},
        color = InterviewCardColor.PastInterviewCardColor
    )
}

sealed class InterviewCardColor {
    object UpcomingInterviewCardColor : InterviewCardColor()
    object ComingNextInterviewColor : InterviewCardColor()
    object PastInterviewCardColor : InterviewCardColor()
}
