package com.twain.interprep.presentation.ui.components.resource

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
import com.twain.interprep.data.ui.ResourceFormData
import com.twain.interprep.presentation.ui.components.generic.IPOutlinedButton
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.BackgroundPalePurple
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun AddLinkCard(
    modifier: Modifier = Modifier,
    getLinkField: (Int) -> String,
    updateLinkField: (Int, String) -> Unit,
    deleteLink: () -> Unit,
    shouldValidate: Boolean,
    isEdit: Boolean,
    numberOfCurrentLinks: Int
) {
    Card(
        shape = Shapes.medium,
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = BackgroundPalePurple)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            ResourceFormData.linkForm.map { input ->
                IPTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    inputText = getLinkField(input.labelTextId),
                    textInputAttributes = input,
                    onTextUpdate = {
                        updateLinkField(input.labelTextId, it)
                    },
                )
            }
            // we will hide the delete button in the adding mode and when there are more than
            // one link on the screen
            if (isEdit && numberOfCurrentLinks > 1){
                IPOutlinedButton(
                    backgroundColor = BackgroundLightPurple,
                    textColor = Color.Black,
                    text = stringResource(id = R.string.menuitem_delete),
                    iconColor = BackgroundDarkPurple,
                    leadingIcon = R.drawable.ic_delete_cross,
                    onClick = { deleteLink() },
                    borderColor = BackgroundDarkPurple,
                    textStyle = MaterialTheme.typography.titleMedium,
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(id = R.dimen.dimension_16dp)
                    )
                )
            }
        }
    }
}
