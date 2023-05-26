package com.twain.interprep.util

import com.twain.interprep.data.model.Interview
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class InterviewsData {
    companion object {
        val interviews = listOf(
            Interview(
                1,
                SimpleDateFormat("dd-MM-yyyy").parse("14-02-2018") ?: Date(),
                "Wattpad"
            ),
            Interview(
                2,
                SimpleDateFormat("dd-MM-yyyy").parse("28-06-2023") ?: Date(),
                "Wattpad"
            ),
            Interview(
                3,
                SimpleDateFormat("dd-MM-yyyy").parse("31-05-2023") ?: Date(),
                "Wattpad"
            ),
            Interview(
                4,
                SimpleDateFormat("dd-MM-yyyy").parse("1-01-2023") ?: Date(),
                "Wattpad"
            ),
            Interview(
                5,
                Date(),
                "Wattpad"
            )
        )
    }
}
