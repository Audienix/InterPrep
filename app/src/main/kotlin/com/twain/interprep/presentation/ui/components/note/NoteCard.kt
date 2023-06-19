package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun NoteCard(){
    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        modifier = androidx.compose.ui.Modifier
            .padding(dimensionResource(id = R.dimen.dimension_8dp))
            .height(106.dp), //TODO change constant height value
    ) {

    }
}