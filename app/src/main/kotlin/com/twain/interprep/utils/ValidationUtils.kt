package com.twain.interprep.utils

import com.twain.interprep.data.ui.TextInputAttributes

/**
 * When shouldValidate is false, we don't need to validate the input text since user hasn't
 * edit the input yet.
 */
fun isValidTextInput(
    shouldValidate: Boolean,
    text: String,
    attributes: TextInputAttributes,
    isError: Boolean
): Boolean {
    return if (shouldValidate) {
        attributes.required && text.trim().isEmpty()
    } else {
        isError
    }
}
