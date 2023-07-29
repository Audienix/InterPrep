package com.twain.interprep.utils

import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.data.ui.ValidationType

/**
 * When shouldValidate is false, we don't need to validate the input text since user hasn't
 * edit the input yet.
 */
fun isValidTextInput(
    isBackPressed: Boolean,
    text: String,
    attributes: TextInputAttributes
): Boolean {
    return if (isBackPressed) {
        validateField(attributes.validationType, text)
    } else {
        when (attributes.validationType) {
            ValidationType.REQUIRED -> validateField(attributes.validationType, text)
            else -> true
        }
    }
}

private fun validateField(validationType: ValidationType, text: String) =
    when (validationType) {
        ValidationType.REQUIRED -> { text.isNotBlank() }
        ValidationType.URL -> {
            text.isBlank() or isValidURL(text.trim())
        }
        ValidationType.NONE -> { true }
    }

fun isValidURL(url: String): Boolean {
    val urlPattern =
        Regex("^((http[s]?|ftp)://)?([a-zA-Z0-9]+\\.)?[a-zA-Z0-9-]+\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?(:[0-9]{2,5})?(/[a-zA-Z0-9+&@#/%?=~_|!:,.;-]*)?(#[-a-zA-Z0-9_]*)?\$")
    return urlPattern.matches(url)
}
