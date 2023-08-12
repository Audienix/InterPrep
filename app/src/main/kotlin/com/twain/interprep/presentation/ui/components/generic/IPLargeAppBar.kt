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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.twain.interprep.R
import com.twain.interprep.data.model.Quote
import com.twain.interprep.presentation.ui.components.interview.NoInterviewCard
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun IPLargeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    todayInterviewCount: MutableIntState,
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
            val interviewCount = todayInterviewCount.intValue
            if (interviewCount == 0)
                NoInterviewTodayDetails()
            else
                InterviewTodayDetails(todayInterviewCount = interviewCount)
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
private fun InterviewTodayDetails(todayInterviewCount: Int) {
    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
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
                text = pluralStringResource(
                    R.plurals.interviews_today,
                    todayInterviewCount,
                    todayInterviewCount
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialColorPalette.onSurface
            )
        }
        if (todayInterviewCount > 0)
            TodayInterviewPager(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                todayInterviewCount = todayInterviewCount
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
                text = stringResource(R.string.no_interviews_today),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialColorPalette.onSurface
            )
        }
        val quotesViewModel: QuotesViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            if (quotesViewModel.currentQuote.quoteId == -1)
                quotesViewModel.getQuotes()
        }

        val quote = quotesViewModel.currentQuote
        NoInterviewCard(quote = quote)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayInterviewPager(modifier: Modifier, todayInterviewCount: Int) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { todayInterviewCount }
    )
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        HorizontalPager(state = pagerState) { page ->

        }
        if (todayInterviewCount > 1) {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                pageCount = todayInterviewCount,
                pagerState = pagerState,
            )
        }
    }
}

@Composable
fun TodayInterviewCard(
    modifier: Modifier = Modifier,
    quote: Quote,
    todayInterviewCount: Int
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
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dimension_4dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_interview_today),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dimension_8dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (todayInterviewCount == 1)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = quote.quote,
                        color = MaterialColorPalette.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
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
            todayInterviewCount = remember { mutableIntStateOf(2) },
            username = "AM",
            isInterviewDetailsVisible = true
        )
    }
}
