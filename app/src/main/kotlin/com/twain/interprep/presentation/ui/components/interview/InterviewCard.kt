package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants.DT_FORMAT_DATE
import com.twain.interprep.constants.StringConstants.DT_FORMAT_DAY
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MONTH_YEAR
import com.twain.interprep.data.model.DashboardInterviewType
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.data.model.isPast
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.presentation.ui.theme.TextPrimary
import com.twain.interprep.presentation.ui.theme.TextSecondary
import com.twain.interprep.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun InterviewCard(
    interview: Interview,
    dashboardInterviewType: DashboardInterviewType,
    navController: NavHostController,
    onClick: () -> Unit,
    onStatusBarClicked: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val cardWidth = configuration.screenWidthDp.dp * dashboardInterviewType.cardWidthFactor
    Box() {
        Column() {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
            ElevatedCard(
                shape = Shapes.medium,
                elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
                colors = CardDefaults.cardColors(containerColor = dashboardInterviewType.cardBackgroundColor),
                modifier = Modifier
                    .width(cardWidth)
                    .padding(dimensionResource(id = R.dimen.dimension_8dp))
                    .clickable(onClick = {
                        onClick()
                        navController.navigate(
                            AppScreens.InterviewDetails.withArgs(
                                interview.interviewId,
                                dashboardInterviewType.cardBackgroundColor.toArgb(),
                                dashboardInterviewType.cardContentColor.toArgb()
                            )
                        ) {
                            popUpTo(AppScreens.Dashboard.route)
                        }
                    })
                    .height(106.dp), //TODO change constant height value
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.dimension_16dp))
                ) {
                    val date = DateUtils.convertDateStringToDate(interview.date)
                    Box(
                        //TODO change constant size value
                        modifier = Modifier
                            .size(80.dp)
                            .clip(Shapes.medium)
                            .background(dashboardInterviewType.cardContentColor),
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
                        Text(
                            text = formatRoundNumAndInterviewType(interview),
                            color = dashboardInterviewType.cardContentColor,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        }
        if (interview.isPast()) {
            InterviewStatusBar(status = interview.interviewStatus, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 12.dp), onClick = { onStatusBarClicked() })
        }
    }
}

/**
 * Format the roundNum and interviewType of the given interview so that when roundNum is empty,
 * only interviewType is shown and when interviewType is empty, only roundNum is shown and if
 * both are empty show "N/A".
 * The result can only be one of the following
 * "N/A"
 * "#${interview.roundNum}" or "${interview.interviewType}"
 * or "#${interview.roundNum} - ${interview.interviewType}".
 *
 * @return the formatted string
 */
fun formatRoundNumAndInterviewType(interview: Interview): String {
    val formattedRoundNum =
        if (interview.roundNum.isNotEmpty())
            "#${interview.roundNum} "
        else ""
    val formattedInterviewType =
        if (interview.roundNum.isNotEmpty() && interview.interviewType.isNotEmpty())
            "- ${interview.interviewType}"
        else interview.interviewType
    return if (formattedRoundNum.isEmpty() && formattedInterviewType.isEmpty()) "N/A"
    else
        formattedRoundNum + formattedInterviewType
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
    InterviewCard(
        interview = interviewMockData,
        onClick = {},
        navController = rememberNavController(),
        dashboardInterviewType = DashboardInterviewType.UpcomingInterview()
    )
}

@Composable
@Preview
fun ComingNextInterviewCard() {
    InterviewCard(
        interview = interviewMockData,
        onClick = {},
        navController = rememberNavController(),
        dashboardInterviewType = DashboardInterviewType.NextInterview()
    )
}

@Composable
@Preview
fun PastInterviewCard() {
    InterviewCard(
        interview = interviewMockData,
        onClick = {},
        navController = rememberNavController(),
        dashboardInterviewType = DashboardInterviewType.PastInterview()
    )
}
