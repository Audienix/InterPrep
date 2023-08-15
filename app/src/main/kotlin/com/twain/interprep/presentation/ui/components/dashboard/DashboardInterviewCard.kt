package com.twain.interprep.presentation.ui.components.dashboard

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.unit.dp
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
import com.twain.interprep.utils.getInterviewCardColorPair
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DashboardInterviewCard(
    interview: Interview,
    interviewType: InterviewType,
    navController: NavHostController,
    onClick: () -> Unit,
    onStatusBarClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val interviewCardColorPair = getInterviewCardColorPair(type = interviewType)
    Box {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dimension_4dp))
            )

            Card(
                border = BorderStroke(
                    dimensionResource(id = R.dimen.dimension_stroke_width_low),
                    MaterialColorPalette.surfaceContainer
                ),
                shape = Shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLowest),
                modifier = Modifier
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
                    .height(106.dp), //TODO change constant height value
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.dimension_16dp))
                ) {
                    val date = DateUtils.convertDateStringToDate(interview.date)
                    IPDateTimeBox(
                        date = date,
                        dateTextColor = interviewCardColorPair.second,
                        monthYearTextColor = interviewCardColorPair.second,
                        backgroundColor = interviewCardColorPair.first,
                        borderColor = Color.Transparent
                    )
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
        }
        if (interview.isPast()) {
            IPInterviewStatus(status = interview.interviewStatus,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = dimensionResource(id = R.dimen.dimension_16dp)),
                onClick = { onStatusBarClicked() })
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
            "Round ${interview.roundNum} "
        else ""
    val formattedInterviewType =
        if (interview.roundNum.isNotEmpty() && interview.interviewType.isNotEmpty())
            "- ${interview.interviewType}"
        else interview.interviewType
    return if (formattedRoundNum.isEmpty() && formattedInterviewType.isEmpty()) ""
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
