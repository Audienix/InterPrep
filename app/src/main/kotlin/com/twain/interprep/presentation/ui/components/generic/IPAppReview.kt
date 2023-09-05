package com.twain.interprep.presentation.ui.components.generic

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun IPAppReview() {
    val localContext = LocalContext.current

    val reviewManager = remember {
        ReviewManagerFactory.create(localContext)
    }

    val reviewInfo = rememberReviewTask(reviewManager)

    LaunchedEffect(key1 = reviewInfo) {
        reviewInfo?.let {
            reviewManager.launchReviewFlow(localContext as Activity, it)
        }
    }
}

@Composable
fun rememberReviewTask(reviewManager: ReviewManager): ReviewInfo? {
    var reviewInfo: ReviewInfo? by remember {
        mutableStateOf(null)
    }
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewInfo = it.result
        }
    }

    return reviewInfo
}
