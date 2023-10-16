package com.twain.interprep.presentation.ui.components.resource

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.data.ui.resourceWithLinks
import com.twain.interprep.presentation.ui.components.generic.IPText
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun ResourceCard(
    resourceAndLinks: Pair<Resource, List<ResourceLink>>,
    showEditIcon: Boolean = true,
    onEditResourceClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(true) }
    val (resource, links) = resourceAndLinks
    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainer
        ),
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLowest)
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
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = MaterialColorPalette.onSurfaceVariant,
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        tint = MaterialColorPalette.onSurfaceVariant,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_16dp)))
            Column(
                modifier = Modifier.weight(8f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
            ) {
                Text(
                    text = resource.topic,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialColorPalette.onSurface
                )
                if (resource.subtopic.isNotEmpty()) {
                    Text(
                        text = resource.subtopic,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialColorPalette.onSurfaceVariant
                    )
                }
                if (isExpanded) {
                    links.forEachIndexed { index, link ->
                        IPText(
                            text = "${index + 1}. ${link.linkDescription.ifEmpty { link.link }}",
                            link = link.link,
                            textColor = MaterialColorPalette.primary
                        )
                    }
                }
            }
            if (showEditIcon) {
                Box(modifier = Modifier.weight(1f)) {
                    IconButton(
                        onClick = onEditResourceClick,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dimension_icon_size_24))
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = "",
                            tint = MaterialColorPalette.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun ResourceCardPreview() {
    ResourceCard(
        resourceAndLinks = resourceWithLinks[1],
        onEditResourceClick = {}
    )
}
