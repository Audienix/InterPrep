package com.twain.interprep.domain.usecase.note

data class NoteUseCase(
    val getAllPastInterviewsWithNotesUseCase: GetAllPastInterviewsWithNotesUseCase,
    val getNotesByInterviewIdUseCase: GetNotesByInterviewIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val insertAllNotesUseCase: InsertAllNotesUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val deleteNotesForInterviewUseCase : DeleteNotesForInterviewUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)
