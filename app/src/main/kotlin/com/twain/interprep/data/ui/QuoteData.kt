package com.twain.interprep.data.ui

import com.twain.interprep.data.model.Quote

class QuoteData {
    companion object {
        val quotes = listOf(
            Quote(
                1,
                "By failing to prepare, you are preparing to fail.",
                "Benjamin Franklin"
            ),
            Quote(
                2,
                "I will prepare and some day my chance will come.",
                "Abraham Lincoln"
            ),
            Quote(
                3,
                "In the fields of observation chance favors only the prepared mind.",
                "Louis Pasteur"
            )
        )
    }
}
