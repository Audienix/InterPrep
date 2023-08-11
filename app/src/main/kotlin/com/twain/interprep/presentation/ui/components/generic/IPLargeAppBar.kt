package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPLargeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    todayInterviewCount: Int,
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
        if (isInterviewDetailsVisible) InterviewTodayDetails(todayInterviewCount)
    }
}

@Composable
private fun InterviewTodayDetails(todayInterviewCount: Int) {
    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
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
                text = "You have $todayInterviewCount interviews today.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialColorPalette.onSurface
            )
        }
        TodayInterviewPager(todayInterviewCount)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayInterviewPager(todayInterviewCount: Int) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { todayInterviewCount }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(state = pagerState) { page ->
            Text(text = "Interview$page")
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pageCount = todayInterviewCount,
            pagerState = pagerState,
        )
    }
}

@Preview
@Composable
fun IPLargeAppBarPreview() {
    InterPrepTheme {
        IPLargeAppBar(
            title = "Hello Arighna",
            subtitle = "Good Morning",
            todayInterviewCount = 3,
            username = "AM",
            isInterviewDetailsVisible = true
        )
    }
}
