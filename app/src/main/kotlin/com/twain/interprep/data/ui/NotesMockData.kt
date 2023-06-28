package com.twain.interprep.data.ui

import com.twain.interprep.data.model.Note

val notesMockData = listOf(
    Note(
        noteId = 0,
        interviewId = 1,
        interviewSegment = "Behavioural",
        topic = "Basic Questions",
        questions = listOf("What are your strengths?", "What are your weaknesses?"),
    ),
    Note(
        noteId = 1,
        interviewId = 1,
        interviewSegment = "Technical",
        topic = "Leetcode Questions",
        questions = listOf(""),
    )
)