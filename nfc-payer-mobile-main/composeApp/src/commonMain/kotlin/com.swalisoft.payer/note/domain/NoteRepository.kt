package com.swalisoft.payer.note.domain
import com.swalisoft.payer.note.data.dto.CreateNoteDto

interface NoteRepository {
    suspend fun createNote(dto: CreateNoteDto) : Note
    suspend fun getNotes(): List<Note>
}