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

fun isValidURL(url: String): Boolean {
    val urlPattern =
        Regex("^((http[s]?|ftp)://)?([a-zA-Z0-9]+\\.)?[a-zA-Z0-9-]+\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?(:[0-9]{2,5})?(/[a-zA-Z0-9+&@#/%?=~_|!:,.;-]*)?(#[-a-zA-Z0-9_]*)?\$")
    return urlPattern.matches(url)
}
