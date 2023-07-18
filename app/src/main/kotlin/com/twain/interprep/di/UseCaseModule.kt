package com.twain.interprep.di

import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.domain.repository.NoteRepository
import com.twain.interprep.domain.repository.QuoteRepository
import com.twain.interprep.domain.usecase.note.GetNoteByInterviewIdUseCase
import com.twain.interprep.domain.usecase.note.GetNoteUseCase
import com.twain.interprep.domain.usecase.note.NoteUseCase
import com.twain.interprep.domain.usecase.interview.DeleteAllInterviewsUseCase
import com.twain.interprep.domain.usecase.interview.DeleteInterviewUseCase
import com.twain.interprep.domain.usecase.interview.GetInterviewByIdUseCase
import com.twain.interprep.domain.usecase.interview.GetInterviewsUseCase
import com.twain.interprep.domain.usecase.interview.GetTypedInterviewsUseCase
import com.twain.interprep.domain.usecase.interview.InsertInterviewUseCase
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.domain.usecase.interview.UpdateInterviewUseCase
import com.twain.interprep.domain.usecase.note.DeleteNotesForInterviewUseCase
import com.twain.interprep.domain.usecase.note.DeleteNoteUseCase
import com.twain.interprep.domain.usecase.note.InsertAllNotesUseCase
import com.twain.interprep.domain.usecase.note.InsertNoteUseCase
import com.twain.interprep.domain.usecase.note.UpdateNoteUseCase
import com.twain.interprep.domain.usecase.quotes.GetQuotesUseCase
import com.twain.interprep.domain.usecase.quotes.InsertQuotesUseCase
import com.twain.interprep.domain.usecase.quotes.QuoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideQuotesUseCase(quoteRepository: QuoteRepository): QuoteUseCase {
        return QuoteUseCase(
            insertQuotesUseCase = InsertQuotesUseCase(quoteRepository),
            getQuotesUseCase = GetQuotesUseCase(quoteRepository)
        )
    }

    @Singleton
    @Provides
    fun provideInterviewUseCase(interviewRepository: InterviewRepository): InterviewUseCase {
        return InterviewUseCase(
            insertInterview = InsertInterviewUseCase(interviewRepository),
            updateInterview = UpdateInterviewUseCase(interviewRepository),
            getInterviews = GetInterviewsUseCase(interviewRepository),
            getTypedInterviews = GetTypedInterviewsUseCase(interviewRepository),
            getInterviewById = GetInterviewByIdUseCase(interviewRepository),
            deleteInterview = DeleteInterviewUseCase(interviewRepository),
            deleteAllInterviews = DeleteAllInterviewsUseCase(interviewRepository)
        )
    }

    @Singleton
    @Provides
    fun provideNoteUseCase(noteRepository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNoteUseCase = GetNoteUseCase(noteRepository),
            getNoteByInterviewIdUseCase = GetNoteByInterviewIdUseCase(noteRepository),
            insertNoteUseCase = InsertNoteUseCase(noteRepository),
            insertAllNotesUseCase = InsertAllNotesUseCase(noteRepository),
            updateNoteUseCase = UpdateNoteUseCase(noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository),
            deleteNotesForInterviewUseCase = DeleteNotesForInterviewUseCase(noteRepository)
        )
    }
}
