package com.twain.interprep.data.model

data class InterviewList(
    val list: List<Interview>,
    val page: Int,
    val hasMore: Boolean
)

data class InterviewListMetaData(
    val pastInterviewList: InterviewList,
    val upcomingInterviewList: InterviewList,
    val comingNextInterviewList: InterviewList
)

fun InterviewListMetaData.isEmpty() = pastInterviewList.list.isEmpty() && upcomingInterviewList.list.isEmpty() && comingNextInterviewList.list.isEmpty()
