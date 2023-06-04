package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun InterviewDetailsCard(modifier: Modifier = Modifier, interview: Interview) {
    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_8dp))
    ) {

    }
}

@Preview
@Composable
fun InterviewDetailsCardPreview(){
    InterviewDetailsCard(interview = interview)
}

