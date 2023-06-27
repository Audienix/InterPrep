package com.twain.interprep.domain.usecase.note

data class NoteUseCase(
    val getNoteUseCase: GetNoteUseCase,
    val getNoteByInterviewIdUseCase: GetNoteByInterviewIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
)
