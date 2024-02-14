package com.twain.interprep.presentation.ui.components.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants.DT_FORMAT_DAY
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.data.model.isPast
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.IPDateTimeBox
import com.twain.interprep.presentation.ui.components.generic.IPInterviewStatus
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.utils.DateUtils
import com.twain.interprep.utils.formatRoundNumAndInterviewType
import com.twain.interprep.utils.getInterviewCardColorPair
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DashboardInterviewCard(
    interview: Interview,
    interviewType: InterviewType,
    navController: NavHostController,
    onClick: () -> Unit,
    onInterviewStatusClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val interviewCardColorPair = getInterviewCardColorPair(type = interviewType)
    Box {
        Card(
            border = BorderStroke(
                dimensionResource(id = R.dimen.dimension_stroke_width_low),
                MaterialColorPalette.surfaceContainer
            ),
            shape = Shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLowest),
            modifier = Modifier.wrapContentSize()
                .padding(vertical = dimensionResource(id = R.dimen.dimension_4dp))
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.dimension_8dp))
                .clickable {
                    onClick()
                    navController.navigate(
                        AppScreens.MainScreens.InterviewDetails.withArgs(
                            interview.interviewId,
                            interviewType,
                        )
                    ) {
                        popUpTo(AppScreens.MainScreens.Dashboard.route)
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dimension_16dp))
                    .padding(dimensionResource(id = R.dimen.dimension_16dp))
            ) {
                val date = DateUtils.convertDateStringToDate(interview.date)
                IPDateTimeBox(
                    modifier = Modifier.fillMaxWidth(0.25f),
                    date = date,
                    dateTextColor = interviewCardColorPair.second,
                    monthYearTextColor = interviewCardColorPair.second,
                    backgroundColor = interviewCardColorPair.first,
                    borderColor = Color.Transparent
                )
                Column(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.dimension_16dp)),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = SimpleDateFormat(
                            DT_FORMAT_DAY,
                            Locale.getDefault()
                        ).format(date).uppercase() + buildString {
                            append(", ")
                        } + DateUtils.getDisplayedTime(context, interview.time),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialColorPalette.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = interview.company,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = MaterialColorPalette.onSurface,
                        style = MaterialTheme.typography.bodyMedium
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
        }
        if (interview.isPast()) {
            IPInterviewStatus(status = interview.interviewStatus,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = dimensionResource(id = R.dimen.dimension_16dp)),
                onClick = { onInterviewStatusClicked() })
        }
    }
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
fun UpcomingInterviewCard() {
    DashboardInterviewCard(
        interview = interviewMockData,
        onClick = {},
        navController = rememberNavController(),
        interviewType = InterviewType.PRESENT
    )
}

@Composable
@Preview
fun ComingNextInterviewCard() {
    DashboardInterviewCard(
        interview = interviewMockData,
        onClick = {},
        navController = rememberNavController(),
        interviewType = InterviewType.FUTURE
    )
}

@Composable
@Preview
fun PastInterviewCard() {
    DashboardInterviewCard(
        interview = interviewMockData,
        onClick = {},
        navController = rememberNavController(),
        interviewType = InterviewType.PAST
    )
}
