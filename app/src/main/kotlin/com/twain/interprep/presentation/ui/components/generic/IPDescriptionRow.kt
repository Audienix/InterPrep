package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple

@Composable
fun IPDescriptionRow(modifier: Modifier = Modifier, title: String, body: String, icon: Int, contentDescription: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row() {
            Icon(
                painterResource(id = icon),
                contentDescription = contentDescription,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dimension_4dp))
                    .size(dimensionResource(id = R.dimen.dimension_24dp)),
                tint = BackgroundDarkPurple
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_16dp)))
            Column() {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_4dp)))
                Text(text = body, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
@Preview
@Composable
fun IPIntroductionRow1Preview() {
    val title = "Manage scheduled job interviews"
    val description = "Effortlessly organize your interview details from a personalized dashboard"
    IPDescriptionRow(title = title, body = description, icon = R.drawable.ic_dashboard_24, contentDescription = "dashboard_icon")
}

@Preview
@Composable
fun IPIntroductionRow2Preview() {
    val title = "Take notes on past interviews"
    val description = "Easily access and manage your past interview notes for future reference"
    IPDescriptionRow(title = title, body = description, icon = R.drawable.ic_note_24, contentDescription = "note_icon")
}

@Preview
@Composable
fun IPIntroductionRow3Preview() {
    val title = "Organize your learning resources"
    val description = "Maximize your interview preparation with a collection of learning materials"
    IPDescriptionRow(title = title, body = description, icon = R.drawable.ic_resource_24, contentDescription = "resource_icon")
}



