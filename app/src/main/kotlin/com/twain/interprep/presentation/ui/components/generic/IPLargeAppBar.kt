package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.presentation.ui.components.interview.NoInterviewCard
import com.twain.interprep.presentation.ui.components.interview.interviewMockData
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.utils.DateUtils

@Composable
fun IPLargeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    todayInterviewList: List<Interview>,
    username: String,
    isInterviewDetailsVisible: Boolean = false
) {
    Column(
        modifier = modifier
            .background(color = MaterialColorPalette.surfaceContainerLow)
            .padding(vertical = dimensionResource(id = R.dimen.dimension_16dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
    ) {
        GreetingsAndProfile(title, username, subtitle)
        if (isInterviewDetailsVisible) {
            if (todayInterviewList.isEmpty())
                NoInterviewTodayDetails()
            else
                InterviewTodayDetails(todayInterviewList = todayInterviewList)
        }
    }
}

@Composable
private fun GreetingsAndProfile(
    title: String,
    username: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
            .wrapContentHeight(align = Alignment.CenterVertically),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialColorPalette.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IPCircleIcon(
            modifier = Modifier
                .weight(0.1f)
                .aspectRatio(1f),
            text = username,
            size = dimensionResource(id = R.dimen.dimension_icon_size_36),
            containerColor = MaterialColorPalette.tertiary,
            borderColor = MaterialColorPalette.onTertiary,
            textColor = MaterialColorPalette.onTertiary,
        )
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
        text = subtitle,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialColorPalette.onSurfaceVariant
    )
}

@Composable
private fun InterviewTodayDetails(todayInterviewList: List<Interview>) {
    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        InterviewTodayReminder(
            message = pluralStringResource(
                R.plurals.interviews_today,
                todayInterviewList.size,
                todayInterviewList.size
            )
        )
        if (todayInterviewList.isNotEmpty())
            TodayInterviewPager(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                interviewList = todayInterviewList
            )
    }
}

@Composable
private fun NoInterviewTodayDetails() {
    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        InterviewTodayReminder(
            message = stringResource(R.string.no_interviews_today)
        )
        val quotesViewModel: QuotesViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            if (quotesViewModel.currentQuote.quoteId == -1)
                quotesViewModel.getQuotes()
        }

        val quote = quotesViewModel.currentQuote
        NoInterviewCard(quote = quote)
    }
}

@Composable
private fun InterviewTodayReminder(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.dimension_4dp)),
            painter = painterResource(id = R.drawable.ic_notification_important_24),
            contentDescription = "contentDescription",
            tint = MaterialColorPalette.primary
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialColorPalette.onSurface
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayInterviewPager(modifier: Modifier, interviewList: List<Interview>) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { interviewList.size }
    )
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        HorizontalPager(state = pagerState) { page ->
            TodayInterviewCard(interview = interviewList[pagerState.currentPage])
        }
        if (interviewList.size > 1) {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                pageCount = interviewList.size,
                pagerState = pagerState,
            )
        }
    }
}

@Composable
fun TodayInterviewCard(
    interview: Interview,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.primary),
        shape = Shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimension_8dp),
                    vertical = dimensionResource(
                        id = R.dimen.dimension_4dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dimension_4dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_reminder_clock),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dimension_8dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.dimension_4dp)
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dimension_icon_size_16)),
                        painter = painterResource(id = R.drawable.ic_timer_24),
                        tint = MaterialColorPalette.tertiaryContainer,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = DateUtils.getDisplayedTime(
                            LocalContext.current,
                            interview.time
                        ),
                        color = MaterialColorPalette.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IPText(
                        text = stringResource(id = R.string.join_here),
                        link = "https://meet.google.com",
                        textColor = MaterialColorPalette.onPrimary,
                        textStyle = MaterialTheme.typography.bodyLarge,
                    )
                }

                if (interview.interviewType.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = interview.interviewType,
                        color = MaterialColorPalette.primaryContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = interview.company,
                    color = MaterialColorPalette.primaryContainer,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview
@Composable
fun IPLargeAppBarPreview() {
    InterPrepTheme {
        IPLargeAppBar(
            title = "Hello Arighna",
            subtitle = "Good Morning",
            todayInterviewList = mutableListOf(interviewMockData),
            username = "AM",
            isInterviewDetailsVisible = true
        )
    }
}
