package com.twain.interprep.presentation.ui.components.resource

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.ui.resourcesMockData
import com.twain.interprep.presentation.ui.components.generic.IPClickableLinkText
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun ResourceCard(
    resource: Resource,
    onEditResourceClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(true) }

    ElevatedCard(
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_16dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            IconToggleButton(
                checked = isExpanded,
                onCheckedChange = { isExpanded = it },
                modifier = Modifier.size(dimensionResource(id = R.dimen.dimension_24dp))
            ) {
                if (isExpanded) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_16dp)))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
            ) {
                Text(
                    text = resource.topic,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (!resource.subtopic.isNullOrEmpty()) {
                    Text(
                        text = resource.subtopic,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (isExpanded) {
                    resource.links.forEachIndexed { index, link ->
                        IPClickableLinkText(
                            text = "${index + 1}. ${link.linkDescription}", // TODO: fix hyperlink
                            textDecoration = TextDecoration.Underline,
                            color = Color.Blue,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onEditResourceClick,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dimension_24dp))
                        .align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                }
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun ResourceCardPreview() {
    ResourceCard(
        resource = resourcesMockData[0],
        onEditResourceClick = {}
    )
}