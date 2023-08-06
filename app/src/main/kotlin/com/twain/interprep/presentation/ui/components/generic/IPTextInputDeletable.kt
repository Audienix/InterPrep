package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple

@Composable
fun IPTextInputDeletable(
    modifier: Modifier = Modifier,
    inputText: String,
    isBackPressed: Boolean = false,
    textInputAttributes: TextInputAttributes,
    onTextUpdate: (text: String) -> Unit,
    onDeleteClicked :() -> Unit,
    showDeleteIcon : Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IPTextInput(
            Modifier.weight(1f),
            inputText = inputText,
            isBackPressed = isBackPressed,
            textInputAttributes = textInputAttributes,
            onTextUpdate = onTextUpdate
        )
        if (showDeleteIcon){
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_4dp)))
            Icon(
                modifier = Modifier.clickable{
                    onDeleteClicked()
                },
                painter = painterResource(R.drawable.outline_do_disturb_on),
                contentDescription = "Delete Icon",
                tint = BackgroundDarkPurple
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_4dp)))
        }
    }

}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun IPTextInputDeletablePreview() {
    Column {
        IPTextInputDeletable(
            modifier = Modifier.fillMaxWidth(),
            textInputAttributes = TextInputAttributes(
                labelTextId = R.string.hint_label_company,
                errorTextId = R.string.error_message_form_input
            ),
            inputText = "",
            onTextUpdate = {},
            onDeleteClicked = {}
        )
    }
}
