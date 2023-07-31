package com.twain.interprep

import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewList
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.data.repository.InterviewRepositoryImpl
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.utils.DateUtils
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class InterviewRepositoryTests {

    private lateinit var interviewRepository: InterviewRepository
    private lateinit var interviewDao: InterviewDAO
    private lateinit var dateUtils: MockedStatic<DateUtils>

    @Before
    fun setup() {
        interviewDao = mock()
        interviewRepository = InterviewRepositoryImpl(interviewDao)
        dateUtils = mockStatic(DateUtils::class.java)
        dateUtils.`when`<String>(DateUtils::getCurrentDateTimeAsString).thenReturn("placeholder")
        dateUtils.`when`<String>(DateUtils::getWeekAfterCurrentDateAsString).thenReturn("placeholder")

        // mock DAO results
        whenever(interviewDao.getPastInterviews(any(), any(), any())).thenReturn(
            flow { emit(PAST_INTERVIEWS) }
        )
        whenever(interviewDao.getUpcomingInterviews(any(), any(), any(), any())).thenReturn(
            flow { emit(UPCOMING_INTERVIEWS) }
        )
        whenever(interviewDao.getComingNextInterviews(any(), any(), any())).thenReturn(
            flow { emit(COMING_NEXT_INTERVIEWS) }
        )
        whenever(interviewDao.getInterview(1)).thenReturn(
            flow { emit(PAST_INTERVIEW_1) }
        )
    }

    @Test
    fun testGetInitialInterviews() = runBlocking {
        val expected = InterviewListMetaData(
            pastInterviewList = InterviewList(PAST_INTERVIEWS, 0, false),
            upcomingInterviewList = InterviewList(UPCOMING_INTERVIEWS, 0, false),
            comingNextInterviewList = InterviewList(COMING_NEXT_INTERVIEWS, 0, false)

        )
        val actual = interviewRepository.getInitialInterviews().single()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetPastInterviews() = runBlocking {
        val expected = PAST_INTERVIEWS
        val actual = interviewRepository.getInterviewList(InterviewType.PAST, 0).single()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetUpcomingInterviews() = runBlocking {
        val expected = UPCOMING_INTERVIEWS
        val actual = interviewRepository.getInterviewList(InterviewType.UPCOMING, 0).single()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetComingNextInterviews() = runBlocking {
        val expected = COMING_NEXT_INTERVIEWS
        val actual = interviewRepository.getInterviewList(InterviewType.COMING_NEXT, 0).single()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetInterviewById() = runBlocking {
        val expected = PAST_INTERVIEW_1
        val actual = interviewRepository.getInterviewById(1).single()
        assertEquals(expected, actual)
    }

    @After
    fun cleanup() {
        dateUtils.close()
    }

    companion object {
        private val PAST_INTERVIEW_1 = Interview(1, "past1", "past1")
        private val PAST_INTERVIEW_2 = Interview(2, "past2", "past2")
        private val UPCOMING_INTERVIEW_1 = Interview(3, "upcoming1", "upcoming1")
        private val UPCOMING_INTERVIEW_2 = Interview(4, "upcoming2", "upcoming2")
        private val COMING_NEXT_INTERVIEW_1 = Interview(5, "comingNext1", "comingNext1")
        private val COMING_NEXT_INTERVIEW_2 = Interview(6, "comingNext2", "comingNext2")

        private val PAST_INTERVIEWS = listOf(PAST_INTERVIEW_1, PAST_INTERVIEW_2)
        private val UPCOMING_INTERVIEWS = listOf(UPCOMING_INTERVIEW_1, UPCOMING_INTERVIEW_2)
        private val COMING_NEXT_INTERVIEWS = listOf(COMING_NEXT_INTERVIEW_1, COMING_NEXT_INTERVIEW_2)
    }
}
